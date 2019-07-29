package com.oecoo.product.dao;

import com.oecoo.product.entity.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryMapper")
public interface CategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Integer checkByCategoryName(String categoryName);

    List<Category> selectCategoryChildByCategoryId(Integer categoryId);

    Category selectByCategoryId(Integer categoryId);

    Integer checkByCategoryId(Integer categoryId);

    int updateCategoryNameByPrimaryKey(Category record);

    int updateCategoryStatusByPrimaryKey(Category category);

    List<Category> selectPaterCategory();

}