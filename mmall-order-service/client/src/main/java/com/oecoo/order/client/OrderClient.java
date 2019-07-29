package com.oecoo.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "mmall-order-service", fallback = OrderClient.OrderClientFallback.class)
public interface OrderClient {

    @Component
    class OrderClientFallback implements OrderClient {

    }
}
