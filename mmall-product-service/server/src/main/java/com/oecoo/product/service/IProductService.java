package com.oecoo.product.service;

import com.github.pagehelper.PageInfo;
import com.oecoo.order.entity.pojo.OrderItem;
import com.oecoo.product.entity.pojo.Product;
import com.oecoo.product.entity.vo.ProductDetailVo;
import com.oecoo.toolset.common.ServerResponse;

import java.util.List;

/**
 * Created by gf on 2018/4/29.
 */
public interface IProductService {

    ServerResponse<String> saveOrUpdateProduct(Product product);

    ServerResponse<String> setProductStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> findProduct(Integer productId, String productName, Integer pageNum, Integer pageSize);

    ServerResponse<ProductDetailVo> getDetailProduct(Integer productId);

    ServerResponse<PageInfo> getProductKeyword(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);

    ServerResponse<Integer> getStockByProductId(Integer productId);

    ServerResponse<Integer> editByPrimaryKeySelective(Product product);

    ServerResponse<Product> findProductByProductId(Integer productId);

    ServerResponse incrProductQuantity(List<List<OrderItem>> orderItems);

    ServerResponse decrProductQuantity(List<OrderItem> orderItems);

}
