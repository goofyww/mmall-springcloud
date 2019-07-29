package com.oecoo.product.controller.provide;

import com.oecoo.product.dao.ProductMapper;
import com.oecoo.product.entity.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * dao服务发布者 productMapper
 *
 * @author gf
 * @date 2019/6/9
 */
@RestController
@RequestMapping("/productMapper/")
public class ProductMapperController {

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("msg.do")
    public String msg() {
        return "This is product msg 02";
    }

    @PostMapping("deleteByPrimaryKey")
    public int deleteByPrimaryKey(Integer id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @PostMapping("insert")
    public int insert(Product record) {
        return productMapper.insert(record);
    }

    @PostMapping("insertSelective")
    public int insertSelective(Product record) {
        return productMapper.insertSelective(record);
    }

    @GetMapping("selectByPrimaryKey")
    public Product selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @PostMapping("updateByPrimaryKeySelective")
    public int updateByPrimaryKeySelective(Product record) {
        return productMapper.updateByPrimaryKeySelective(record);
    }

    @PostMapping("updateByPrimaryKey")
    public int updateByPrimaryKey(Product record) {
        return productMapper.updateByPrimaryKey(record);
    }

    @GetMapping("checkProductByName")
    public int checkProductByName(String productName) {
        return productMapper.checkProductByName(productName);
    }

    @GetMapping("checkProductById")
    public int checkProductById(Integer productId) {
        return productMapper.checkProductById(productId);
    }

    @GetMapping("selectProductList")
    public List<Product> selectProductList() {
        return productMapper.selectProductList();
    }

    @GetMapping("selectByProductName")
    public List<Product> selectByProductName(Integer productId, String productName) {
        return productMapper.selectByProductName(productId, productName);
    }

    @GetMapping("selectByNameAndCategoryIds")
    public List<Product> selectByNameAndCategoryIds(String keyword, List<Integer> categoryIds) {
        return productMapper.selectByNameAndCategoryIds(keyword, categoryIds);
    }

    //这里一定要用Integer，因为int无法为null，考虑到很多商品已经删除的情况。
    @GetMapping("selectStockByProductId")
    public Integer selectStockByProductId(Integer id) {
        return productMapper.selectStockByProductId(id);
    }

}
