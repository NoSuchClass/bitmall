package com.bitmall.controller.portal;

import com.bitmall.common.ServiceResponse;
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
    public ServiceResponse getDetail(Integer productId) {
        return iProductService.getDetail(productId);
    }

    public ServiceResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                          @RequestParam(value = "category", required = false) Integer category,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageNum", defaultValue = "10")  int pageSize) {

    }
}
