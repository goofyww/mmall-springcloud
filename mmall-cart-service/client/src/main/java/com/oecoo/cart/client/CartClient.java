package com.oecoo.cart.client;

import com.oecoo.cart.entity.pojo.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 购物车系统对外接口
 */
@FeignClient(value = "mmall-cart-service", fallback = CartClient.CartClientFallback.class)
public interface CartClient {

    @GetMapping("/cartMapper/selectCheckedCartByUserId/{userId}")
    List<Cart> selectCheckedCartByUserId(@PathVariable(value = "userId") Integer userId);

    @GetMapping("/cartMapper/selectCartByUserId/{userId}")
    List<Cart> selectCartByUserId(@PathVariable(value = "userId") Integer userId);

    @Component
    class CartClientFallback implements CartClient {

        @Override
        public List<Cart> selectCheckedCartByUserId(Integer userId) {
            return null;
        }

        @Override
        public List<Cart> selectCartByUserId(Integer userId) {
            return null;
        }
    }

}
