package com.bitmall.service;

import com.bitmall.common.ServiceResponse;
import com.bitmall.dataobject.ProductDO;
import com.github.pagehelper.PageInfo;

public interface IProductService {
    ServiceResponse saveOrUpdateProduct(ProductDO product);
    ServiceResponse setSaleStatus(Integer productId, Integer status);
    ServiceResponse manageProductDetail(Integer productId);
    ServiceResponse<PageInfo> getList(int pageNum, int pageSize);
    ServiceResponse productSearch(String productName, Integer productId, int pageNum, int pageSize);
    ServiceResponse getDetail(Integer productId);
}
