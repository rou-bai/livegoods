package com.livegoods;

import org.springframework.amqp.core.AmqpTemplate;

/*
发送消息到工具类型
 */
public class MessagePublisher {
    private AmqpTemplate amqpTemplate;

    /*
    发送同步消息，等待返回
     */
    public Object sendMessageSync(String exchange, String routeKey, Object messagePayload){
        return amqpTemplate.convertSendAndReceive(exchange, routeKey, messagePayload);
    }

    /*
    发送异步消息
     */
    public void sendMessageAsync(String exchange, String routeKey, Object messagePayload){
        amqpTemplate.convertAndSend(exchange, routeKey, messagePayload);
    }

    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

}
