package com.oecoo.product.entity.transfer;

import com.oecoo.product.entity.pojo.Category;
import com.oecoo.product.entity.vo.CategoryVo;

/**
 * @author gf
 * @date 2019/6/30 17:34
 */
public class CategoryTransfer {

    public static Category toPO(CategoryVo vo) {
        Category category = new Category();

        category.setId(vo.getId());
        category.setName(vo.getName());
        category.setParentId(vo.getParentId());
        category.setSortOrder(vo.getSortOrder());
        category.setStatus(vo.getStatus());
        return category;
    }

    public static CategoryVo toVO(Category po) {
        CategoryVo categoryVo = new CategoryVo();

        categoryVo.setId(po.getId());
        categoryVo.setName(po.getName());
        categoryVo.setParentId(po.getParentId());
        categoryVo.setStatus(po.getStatus());
        categoryVo.setSortOrder(po.getSortOrder());
        return categoryVo;
    }

}
