package com.bitmall.dao;

import com.bitmall.dataobject.ShippingDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShippingDO record);

    int insertSelective(ShippingDO record);

    ShippingDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShippingDO record);

    int updateByPrimaryKey(ShippingDO record);

    int deleteByShippingIdUserId(@Param("userId")Integer userId,@Param("shippingId") Integer shippingId);

    int updateByShipping(ShippingDO record);

    ShippingDO selectByShippingIdUserId(@Param("userId")Integer userId, @Param("shippingId") Integer shippingId);

    List<ShippingDO> selectByUserId(@Param("userId")Integer userId);

}