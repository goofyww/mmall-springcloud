package com.oecoo.order.controller.restful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring cloud stream Sender example
 *
 * @author gf
 * @date 2019/7/21 10:05
 */
@RestController
@Slf4j
public class SendMessageController {

//    @Autowired
//    private OrderSender orderSender;
//
//    @GetMapping("/sendMessage/{message}")
//    public void sendV1(@PathVariable("message") String message) {
//        log.info("sendV1 start");
//        orderSender.outputV1().send(MessageBuilder.withPayload(message).build());
//    }
//
//    @GetMapping("/sendMessage")
//    public void sendV2() {
//        log.info("sendV2 start");
//        Product product = new Product();
//        product.setId(37);
//        product.setCategoryId(100006);
//        product.setName("4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春");
//        product.setSubtitle("NOVA青春版1999元");
//        product.setMainImage("0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg");
//        orderSender.outputV2().send(MessageBuilder.withPayload(product).build());
//    }

}
