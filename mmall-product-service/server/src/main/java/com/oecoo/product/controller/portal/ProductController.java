package com.oecoo.product.controller.portal;

import com.github.pagehelper.PageInfo;
import com.oecoo.product.entity.vo.ProductDetailVo;
import com.oecoo.product.service.IProductService;
import com.oecoo.toolset.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gf on 2018/5/1.
 */
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    //商品详情
    @GetMapping(value = "detail.do")
    public ServerResponse<ProductDetailVo> detailProduct(Integer productId) {
        return iProductService.getDetailProduct(productId);
    }

    //RSETFul API
    @GetMapping(value = "detail/{productId}")
    public ServerResponse<ProductDetailVo> detailProductRESTful(@PathVariable Integer productId) {
        return iProductService.getDetailProduct(productId);
    }

    //商品列表
    @GetMapping(value = "list.do")
    public ServerResponse<PageInfo> listProduct(@RequestParam(value = "keyword", required = false) String keyword,
                                                @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iProductService.getProductKeyword(keyword, categoryId, pageNum, pageSize, orderBy);
    }

}
