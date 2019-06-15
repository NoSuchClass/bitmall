package com.bitmall.service;

import com.bitmall.common.ServiceResponse;
import com.bitmall.dataobject.CategoryDO;

import java.util.List;

public interface ICategoryService {

    ServiceResponse addCategory(String categoryName, Integer parentId);
    ServiceResponse updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 获取子节点
     * @param categoryId
     * @return
     */
    ServiceResponse<List<CategoryDO>> getChildrenByParentId(Integer categoryId);

    /**
     * 递归查询本节点id及其子节点id
     * @param categoryId
     * @return
     */
    ServiceResponse selectCategoryAndChildrenById(Integer categoryId);
}
