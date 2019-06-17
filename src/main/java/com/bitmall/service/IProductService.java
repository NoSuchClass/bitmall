package com.bitmall.service;

import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.ProductDO;
import com.github.pagehelper.PageInfo;

public interface IProductService {
    ServerResponse saveOrUpdateProduct(ProductDO product);
    ServerResponse setSaleStatus(Integer productId, Integer status);
    ServerResponse manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getList(int pageNum, int pageSize);
    ServerResponse productSearch(String productName, Integer productId, int pageNum, int pageSize);
    ServerResponse getProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
