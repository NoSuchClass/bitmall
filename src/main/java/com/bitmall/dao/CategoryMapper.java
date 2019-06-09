package com.bitmall.dao;

import com.bitmall.dataobject.CategoryDO;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryDO record);

    int insertSelective(CategoryDO record);

    CategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryDO record);

    int updateByPrimaryKey(CategoryDO record);

    List<CategoryDO> selectCategoryChildrenByParentId(Integer parentId);
}