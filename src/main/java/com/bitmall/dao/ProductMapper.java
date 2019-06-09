package com.bitmall.dao;

import com.bitmall.dataobject.ProductDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductDO record);

    int insertSelective(ProductDO record);

    ProductDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductDO record);

    int updateByPrimaryKey(ProductDO record);

    List<ProductDO> selectList();

    List<ProductDO> selectByNameAndProductId(@Param("productName")String productName, @Param("productId") Integer productId);

    List<ProductDO> selectByNameAndCategoryIds(@Param("productName")String productName, @Param("categoryIdList")List<Integer> categoryIdList);


}