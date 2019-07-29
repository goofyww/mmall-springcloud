package com.oecoo.user.controller.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gf
 * @date 2019/7/10 14:42
 */
@Controller
@RequestMapping("/mq/")
@Slf4j
public class MqController {

    @Autowired
    AmqpTemplate amqpTemplate;

    @GetMapping("send")
    public String send(String message) {
        log.info("mq 发送消息");
        amqpTemplate.convertAndSend("orderQueue", message);
        return message;
    }

}
