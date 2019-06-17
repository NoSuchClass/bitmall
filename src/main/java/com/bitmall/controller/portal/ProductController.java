package com.bitmall.controller.portal;

import com.bitmall.common.ServerResponse;
import com.bitmall.service.IProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyuehe
 * @date 2019/6/16 0:19
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService iProductService;

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ServerResponse getDetail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageNum", defaultValue = "10")  int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "")  String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
