package com.bitmall.dao;

import com.bitmall.dataobject.CartDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CartDO record);

    int insertSelective(CartDO record);

    CartDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CartDO record);

    int updateByPrimaryKey(CartDO record);

    CartDO selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId")Integer productId);

    List<CartDO> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByUserIdProductIds(@Param("userId") Integer userId,@Param("productIdList")List<String> productIdList);


    int checkedOrUncheckedProduct(@Param("userId") Integer userId,@Param("productId")Integer productId,@Param("checked") Integer checked);

    int selectCartProductCount(@Param("userId") Integer userId);


    List<CartDO> selectCheckedCartByUserId(Integer userId);


}