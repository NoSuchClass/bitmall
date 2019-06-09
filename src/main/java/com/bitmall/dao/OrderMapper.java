package com.bitmall.dao;

import com.bitmall.dataobject.OrderDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);

    OrderDO selectByUserIdAndOrderNo(@Param("userId")Integer userId, @Param("orderNo")Long orderNo);


    OrderDO selectByOrderNo(Long orderNo);



    List<OrderDO> selectByUserId(Integer userId);


    List<OrderDO> selectAllOrder();
}