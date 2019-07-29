package com.oecoo.cart.controller.provide;

import com.oecoo.cart.dao.CartMapper;
import com.oecoo.cart.entity.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gf
 * @date 2019/7/6 2:06
 */
@RestController
@RequestMapping("/cartMapper/")
public class CartMapperClient {

    @Autowired
    private CartMapper cartMapper;

    @GetMapping("selectCheckedCartByUserId/{userId}")
    public List<Cart> selectCheckedCartByUserId(@PathVariable(value = "userId") Integer userId) {
        return cartMapper.selectCheckedCartByUserId(userId);
    }

    @GetMapping("selectCartByUserId/{userId}")
    public List<Cart> selectCartByUserId(@PathVariable(value = "userId") Integer userId) {
        return cartMapper.selectCartByUserId(userId);
    }


}
