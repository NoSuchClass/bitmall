package com.bitmall.controller.backend;

import com.bitmall.common.Const;
import com.bitmall.common.ResponseCode;
import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.UserDO;
import com.bitmall.service.IOrderService;
import com.bitmall.service.IUserService;
import com.bitmall.vo.OrderVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author liuyuehe
 * @date 2019/6/19 13:54
 */
@RestController
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    IOrderService iOrderService;
    @Autowired
    IUserService iUserService;

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        UserDO UserDO = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (UserDO == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(UserDO).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iOrderService.manageList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse<OrderVO> orderDetail(HttpSession session, Long orderNo) {

        UserDO UserDO = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (UserDO == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(UserDO).isSuccess()) {
            //填充我们增加产品的业务逻辑

            return iOrderService.manageDetail(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    @RequestMapping("search")
    public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        UserDO UserDO = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (UserDO == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(UserDO).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    @RequestMapping("send_goods")
    public ServerResponse<String> orderSendGoods(HttpSession session, Long orderNo) {

        UserDO UserDO = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (UserDO == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(UserDO).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iOrderService.manageSendGoods(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
