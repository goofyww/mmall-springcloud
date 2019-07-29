package com.oecoo.order.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * rabbitmq 监听 example
 *
 * @author gf
 * @date 2019/7/10 9:49
 */
//@Component
@Slf4j
public class MqReceiver {

    // 3. 自动创建, Exchange 和 Queue绑定
    // 1. @RabbitListener(queues = "orderQueue")
    // 2. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("orderQueue"))
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerQueue"),
            key = "computer",
            exchange = @Exchange("myExchange")
    ))
    public void computerReceiver(String message) {
        log.info("computerReceiver receiver message is {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("goodsQueue"),
            key = "goods",
            exchange = @Exchange("myExchange")
    ))
    public void goodsReceiver(String message) {
        log.info("goodsReceiver receiver message is {}", message);
    }

}
