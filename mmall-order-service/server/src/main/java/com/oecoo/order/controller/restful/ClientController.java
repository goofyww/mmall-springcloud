package com.oecoo.order.controller.restful;

import com.oecoo.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author gf
 * @date 2019/6/9
 */
@RestController
@RequestMapping("/restful/")
@Slf4j
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("sendMsg.do")
    public void send() {
        amqpTemplate.convertAndSend("orderQueue", "hello rabbitmq !");
    }

    @GetMapping("msgmove.do")
    public String msgmove() {
        return productClient.msg();
    }

    @GetMapping("msg.do")
    public String msg() {
        // 方式一 (直接使用restTemplate,url写死)
        // RestTemplate restTemplate = new RestTemplate();
        // String response = restTemplate.getForObject("http://www.oecoo.com:9000/restful/msg.do",String.class);

        // 方式二 (使用loadBalancerClient通过应用名获取url,然后再使用)
        // ServiceInstance serviceInstance = loadBalancerClient.choose("MMALL-PRODUCT-SERVICE");
        // String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort() + "/provide/msg.do");
        // RestTemplate restTemplate = new RestTemplate();
        // String response = restTemplate.getForObject(url,String.class);

        // 方式三 (利用@LoadBalanced，可在restTemplate里使用应用名字)
        String response = restTemplate.getForObject("http://MMALL-PRODUCT-SERVICE/productMapper/msg.do", String.class);

        log.info("response = {}", response);
        return response;
    }

}
