package com.bitmall.service.impl;

import com.bitmall.common.ServerResponse;
import com.bitmall.dao.CategoryMapper;
import com.bitmall.dataobject.CategoryDO;
import com.bitmall.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author liuyuehe
 * @date 2019/6/15 12:00
 */
@Service(value = "iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("添加商品参数错误");
        }

        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setName(categoryName);
        categoryDO.setParentId(parentId);
        categoryDO.setStatus(true);
        int rowCount = categoryMapper.insert(categoryDO);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("商品添加成功");
        }
        return ServerResponse.createByErrorMessage("商品添加失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("更新商品参数错误");
        }
        CategoryDO categoryDO =  new CategoryDO();
        categoryDO.setName(categoryName);
        categoryDO.setId(categoryId);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(categoryDO);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新品类名称成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名称失败");
    }

    @Override
    public ServerResponse<List<CategoryDO>> getChildrenByParentId(Integer categoryId) {
        List<CategoryDO> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<CategoryDO> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        for(CategoryDO categoryItem : categorySet) {
            categoryIdList.add(categoryItem.getId());
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    public Set<CategoryDO> findChildCategory(Set<CategoryDO> categorySet, Integer categoryId) {
        CategoryDO category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<CategoryDO> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(CategoryDO categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
