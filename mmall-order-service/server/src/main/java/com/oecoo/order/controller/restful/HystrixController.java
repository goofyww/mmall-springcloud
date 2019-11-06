package com.oecoo.order.controller.restful;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.oecoo.product.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gf
 * @date 2019/8/4 20:37
 */
@RestController
@RequestMapping("/hystrix/")
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    @Autowired
    private ProductClient productClient;

    // 降级设置
//     fallback 为方法名，不声明 fallbackMethod的值，则走的是默认的 defaultfallback方法
//    @HystrixCommand(fallbackMethod = "fallback")
    // 超时设置
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    }, fallbackMethod = "fallback")
    @GetMapping("msg.do")
    public String getProductInfoList() throws InterruptedException {
//        也可用于本服务抛出异常，触发降级
//        throw new RuntimeException("发生异常，触发fallback方法");
        return productClient.msg();
    }

    // 声明在方法上 ，仅为单点的某个方法提供
    public String fallback() {
        return "网络开小差了，请稍后再试~~";
    }

    // 声明在Controller类，为本类所有的方法 提供一个统一默认
    public String defaultFallback() {
        return "默认提示：网络开小差了，请稍后再试~~";
    }


}
