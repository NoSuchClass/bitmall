package com.bitmall.dao;

import com.bitmall.dataobject.OrderItemDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItemDO record);

    int insertSelective(OrderItemDO record);

    OrderItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItemDO record);

    int updateByPrimaryKey(OrderItemDO record);

    List<OrderItemDO> getByOrderNoUserId(@Param("orderNo")Long orderNo, @Param("userId")Integer userId);

    List<OrderItemDO> getByOrderNo(@Param("orderNo")Long orderNo);



    void batchInsert(@Param("orderItemList") List<OrderItemDO> orderItemList);


}