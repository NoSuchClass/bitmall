package com.bitmall.controller.backend;

import com.bitmall.common.Const;
import com.bitmall.common.ResponseCode;
import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.UserDO;
import com.bitmall.service.ICategoryService;
import com.bitmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author liuyuehe
 * @date 2019/6/15 11:48
 */
@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    IUserService iUserService;
    @Autowired
    ICategoryService iCategoryService;

    @RequestMapping(value = "add_category", method = RequestMethod.POST)
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "update_category_name", method = RequestMethod.POST)
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categotyId) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrenByParentId(categotyId);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }

    @RequestMapping(value = "get_deep_category", method = RequestMethod.GET)
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categotyId) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.selectCategoryAndChildrenById(categotyId);
        } else {
            return ServerResponse.createByErrorMessage("该操作需要管理员权限");
        }
    }
}
