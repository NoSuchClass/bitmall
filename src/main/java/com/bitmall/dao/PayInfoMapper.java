package com.bitmall.dao;

import com.bitmall.dataobject.PayInfoDO;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayInfoDO record);

    int insertSelective(PayInfoDO record);

    PayInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfoDO record);

    int updateByPrimaryKey(PayInfoDO record);
}