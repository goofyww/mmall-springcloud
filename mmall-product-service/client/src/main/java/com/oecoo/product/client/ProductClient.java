package com.oecoo.product.client;

import com.oecoo.product.entity.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mmall-product-service", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/productMapper/msg.do")
    String msg();

    @GetMapping("/productMapper/selectByPrimaryKey")
    Product selectByPrimaryKey(@RequestParam("id") Integer id);

    @GetMapping("/productMapper/checkProductById")
    int checkProductById(@RequestParam("productId") Integer productId);

    @Component
    class ProductClientFallback implements ProductClient {

        public String msg() {
            return null;
        }

        public Product selectByPrimaryKey(Integer id) {
            return null;
        }

        public int checkProductById(Integer productId) {
            return 0;
        }
    }

}
