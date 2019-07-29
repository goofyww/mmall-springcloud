package com.oecoo.cart.dao;

import com.oecoo.cart.entity.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cartMapper")
public interface CartMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdAndProductId(@Param(value = "userId") Integer userId, @Param(value = "productId") Integer productId);

    List<Cart> selectCartByUserId(Integer userId);

    Integer selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByUserIdProductIds(@Param(value = "userId") Integer userId, @Param(value = "productIdList") List<String> productIds);

    int checkOrUncheckedProduct(@Param(value = "userId") Integer userId, @Param(value = "productId") Integer productId, @Param(value = "checked") Integer checked);

    Integer selectCartProductCountByUserId(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);

}