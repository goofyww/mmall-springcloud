package com.oecoo.product.service;

import com.oecoo.product.entity.pojo.Category;
import com.oecoo.product.entity.vo.CategoryVo;
import com.oecoo.toolset.common.ServerResponse;

import java.util.List;

/**
 * Created by gf on 2018/4/29.
 */
public interface ICategoryService {

    ServerResponse<String> addCategory(String categoryName, Integer parentId);

    ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

    ServerResponse<String> updateCategoryStatus(Integer categoryId, Integer status);

    ServerResponse removeCategoryById(Integer categoryId);

    ServerResponse<List<CategoryVo>> getCategoryList();

}
