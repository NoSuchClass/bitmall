package com.bitmall.controller.backend;

import com.bitmall.common.Const;
import com.bitmall.common.ResponseCode;
import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.ProductDO;
import com.bitmall.dataobject.UserDO;
import com.bitmall.service.IFileService;
import com.bitmall.service.IProductService;
import com.bitmall.service.IUserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyuehe
 * @date 2019/6/15 15:30
 */
@RestController
@RequestMapping("/manage/product/")
public class ProductManageController {
    @Autowired
    IUserService iUserService;
    @Autowired
    IProductService iProductService;
    @Autowired
    IFileService iFileService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ServerResponse saveProduct(HttpSession session, ProductDO product) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "set_sale_status", method = RequestMethod.POST)
    public ServerResponse saveProduct(HttpSession session, Integer productId, Integer status) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ServerResponse productSearch(HttpSession session, String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.productSearch(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ServerResponse upload(HttpSession session, @RequestParam("upload_file") MultipartFile file, HttpServletRequest request) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            HashMap<String, String> hashMap = iFileService.upload(file, path);
            return ServerResponse.createBySuccess(hashMap);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping("rich_text_img_upload")
    public Map richTextImgUpload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        String path = request.getSession().getServletContext().getRealPath("upload");
        HashMap<String, String> hashMap = iFileService.upload(file, path);
        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",hashMap.get("url"));
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

}
