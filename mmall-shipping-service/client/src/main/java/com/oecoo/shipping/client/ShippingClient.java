package com.oecoo.shipping.client;

import com.oecoo.shipping.entity.pojo.Shipping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mmall-shipping-service", fallback = ShippingClient.ShippingClientFallback.class)
public interface ShippingClient {

    @GetMapping("/shippingMapper/selectByPrimaryKey")
    Shipping selectByPrimaryKey(@RequestParam("id") Integer id);

    @Component
    class ShippingClientFallback implements ShippingClient {

        public Shipping selectByPrimaryKey(Integer id) {
            return null;
        }
    }
}
