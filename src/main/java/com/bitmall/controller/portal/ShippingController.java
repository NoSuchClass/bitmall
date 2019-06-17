package com.bitmall.controller.portal;

import com.bitmall.common.Const;
import com.bitmall.common.ResponseCode;
import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.ShippingDO;
import com.bitmall.dataobject.UserDO;
import com.bitmall.service.IShippingService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author liuyuehe
 * @date 2019/6/17 14:50
 */
@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private IShippingService iShippingDOService;


    @RequestMapping("add")
    public ServerResponse add(HttpSession session, ShippingDO ShippingDO) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingDOService.add(user.getId(), ShippingDO);
    }


    @RequestMapping("delete")
    public ServerResponse delete(HttpSession session, Integer shippingId) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingDOService.delete(user.getId(), shippingId);
    }

    @RequestMapping("update")
    public ServerResponse update(HttpSession session, ShippingDO ShippingDO) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingDOService.update(user.getId(), ShippingDO);
    }


    @RequestMapping("select")
    public ServerResponse<ShippingDO> select(HttpSession session, Integer shippingId) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingDOService.select(user.getId(), shippingId);
    }


    @RequestMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingDOService.list(user.getId(), pageNum, pageSize);
    }
}