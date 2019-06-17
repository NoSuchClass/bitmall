package com.bitmall.service;

import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.ShippingDO;
import com.github.pagehelper.PageInfo;

public interface IShippingService {
    ServerResponse add(Integer userId, ShippingDO shipping);
    ServerResponse<String> delete(Integer userId,Integer shippingId);
    ServerResponse update(Integer userId, ShippingDO shipping);
    ServerResponse<ShippingDO> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
