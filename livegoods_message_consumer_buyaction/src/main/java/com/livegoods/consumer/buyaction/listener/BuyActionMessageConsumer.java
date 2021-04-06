package com.livegoods.consumer.buyaction.listener;

import com.livegoods.commons.message.BuyActionMessage;
import com.livegoods.pojo.Houses;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BuyActionMessageConsumer {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final String lockValue = "1";
    @Value("${livegoods.lock.buyAction}")
    private String lockKey;
    // 自旋超时阈值,自旋就是遍历多次调用，增加容错性
    private final long times = 1000;
    @Value("${livegoods.house.redisPrefix}")
    private String housePrefix;

    /**
     * 处理秒杀消息
     *  1、 访问redis，获取分布式锁。锁设定一个有效期。
     *  2、 判断锁是否获取成功。
     *  3、 获取失败，自旋等待，获取锁后，进入后续流程。
     *  4、 获取成功，访问redis，查询房屋数据
     *  5、 修改房屋数据库存
     *  6、 更新redis中的房屋缓存
     *  7、 释放分布式锁标记
     *  8、 返回秒杀结果
     * @param message
     * @return
     */

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "${livegoods.amqp.buyaction.queueName}", autoDelete = "false"),
                    exchange = @Exchange(name="${livegoods.amqp.buyaction.exchange}", type="${livegoods.amqp.buyaction.exchangeType}"),
                    key = "${livegoods.amqp.buyaction.routingKey}"
            )
    })
    public boolean onMessage(BuyActionMessage message){
        long localTimes = 0;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        while(localTimes < times){
            //获取分布式锁
            if(valueOperations.setIfAbsent(lockKey, lockValue, 2, TimeUnit.SECONDS)){
                try{
                    //获取锁标记
                    //查询details接口存储的房屋缓存信息
                    String houseKey = housePrefix + "(" + message.getHouseId() + ")";
                    Houses houses = (Houses) valueOperations.get(houseKey);
                    //判断库存
                    if(houses == null || houses.getNums() <= 0){
                        return false;
                    }else{
                        houses.setNums(houses.getNums() - 1);
                        valueOperations.set(houseKey, houses);
                        return true;
                    }
                }finally{
                    //删除分布式锁
                    redisTemplate.delete(lockKey);
                }
            }

            //取不到分布式锁时，自旋
            try{
                localTimes += 100;
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
