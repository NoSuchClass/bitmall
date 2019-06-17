package com.bitmall.service;

import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.CategoryDO;

import java.util.List;

public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 获取子节点
     * @param categoryId
     * @return
     */
    ServerResponse<List<CategoryDO>> getChildrenByParentId(Integer categoryId);

    /**
     * 递归查询本节点id及其子节点id
     * @param categoryId
     * @return
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
