package com.livegoods.buyaction.service.impl;

import com.livegoods.MessagePublisher;
import com.livegoods.buyaction.service.BuyActionService;
import com.livegoods.commons.message.BuyActionMessage;
import com.livegoods.commons.message.OrderMessage;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class BuyActionServiceImpl implements BuyActionService {
    @Autowired
    private MessagePublisher messagePublisher;
    @Value("${livegoods.amqp.buyaction.exchange}")
    private String exchange;
    @Value("${livegoods.amqp.buyaction.routingKey}")
    private String routingKey;
    @Value("${livegoods.amqp.order.exchange}")
    private String orderExchange;
    @Value("${livegoods.amqp.order.routingKey}")
    private String orderRoutingKey;

    @Override
    public Result<Object> buyAction(String id, String user){
        BuyActionMessage message = new BuyActionMessage();
        message.setHouseId(id);
        boolean buyResult = (boolean)messagePublisher.sendMessageSync(exchange, routingKey, message);
        Result<Object> result = new Result<>();
        if(buyResult){
            //秒杀成功，发送异步消息mongo内创建订单，更新库存
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setUser(user);
            orderMessage.setHouseId(id);
            messagePublisher.sendMessageAsync(orderExchange, orderRoutingKey, orderMessage);

            result.setStatus(200);
            result.setMsg("预定成功啦");
        }else{
            result.setStatus(500);
            result.setMsg("预定失败啦");
        }
        return result;
    }
}
