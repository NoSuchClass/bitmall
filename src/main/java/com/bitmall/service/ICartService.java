package com.bitmall.service;

import com.bitmall.common.ServerResponse;
import com.bitmall.vo.CartVO;

public interface ICartService {
    ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVO> update(Integer userId,Integer productId,Integer count);
    ServerResponse<CartVO> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVO> list (Integer userId);
    ServerResponse<CartVO> selectOrUnSelect (Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
