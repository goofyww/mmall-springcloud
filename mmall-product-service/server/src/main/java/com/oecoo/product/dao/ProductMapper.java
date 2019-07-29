package com.oecoo.product.dao;

import com.oecoo.product.entity.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productMapper")
public interface ProductMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Integer checkProductByName(String productName);

    Integer checkProductById(Integer productId);

    List<Product> selectProductList();

    List<Product> selectByProductName(@Param("productId") Integer productId, @Param("productName") String productName);

    List<Product> selectByNameAndCategoryIds(@Param("keyword") String keyword, @Param("categoryIds") List<Integer> categoryIds);

    //这里一定要用Integer，因为int无法为null，考虑到很多商品已经删除的情况。
    Integer selectStockByProductId(Integer id);

}