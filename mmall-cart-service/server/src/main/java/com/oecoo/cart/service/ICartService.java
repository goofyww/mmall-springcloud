package com.oecoo.cart.service;

import com.oecoo.cart.entity.pojo.Cart;
import com.oecoo.cart.entity.vo.CartVo;
import com.oecoo.toolset.common.ServerResponse;

import java.util.List;

/**
 * Created by gf on 2018/5/2.
 */
public interface ICartService {

    ServerResponse<CartVo> addCart(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> removeCartProduct(Integer userId, String productIds);

    ServerResponse<CartVo> updateCart(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);

    int removeById(Integer id);

    ServerResponse cleanCarts(List<Cart> carts);

}
