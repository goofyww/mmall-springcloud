package com.oecoo.order.mq;

import com.oecoo.order.OrderServiceApplicationTest;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqReceiverTest extends OrderServiceApplicationTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void computerSend() {
        amqpTemplate.convertAndSend("myExchange", "computer", "hello computerKey!");
    }

    @Test
    public void goodsSend() {
        amqpTemplate.convertAndSend("myExchange", "goods", "hello goodsKey!");
    }

}