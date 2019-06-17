package com.bitmall.controller.portal;

import com.bitmall.common.Const;
import com.bitmall.common.ResponseCode;
import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.UserDO;
import com.bitmall.service.ICartService;
import com.bitmall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author liuyuehe
 * @date 2019/6/16 14:13
 */
@RestController
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;



    @RequestMapping("list")
    @ResponseBody
    public ServerResponse<CartVO> list(HttpSession session){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(UserDO.getId());
    }

    @RequestMapping("add")
    @ResponseBody
    public ServerResponse<CartVO> add(HttpSession session, Integer count, Integer productId){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(UserDO.getId(),productId,count);
    }



    @RequestMapping("update")
    @ResponseBody
    public ServerResponse<CartVO> update(HttpSession session, Integer count, Integer productId){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(UserDO.getId(),productId,count);
    }

    @RequestMapping("delete_product")
    @ResponseBody
    public ServerResponse<CartVO> deleteProduct(HttpSession session,String productIds){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(UserDO.getId(),productIds);
    }


    @RequestMapping("select_all")
    @ResponseBody
    public ServerResponse<CartVO> selectAll(HttpSession session){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(UserDO.getId(),null,Const.Cart.CHECKED);
    }

    @RequestMapping("un_select_all")
    @ResponseBody
    public ServerResponse<CartVO> unSelectAll(HttpSession session){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(UserDO.getId(),null,Const.Cart.UN_CHECKED);
    }



    @RequestMapping("select")
    @ResponseBody
    public ServerResponse<CartVO> select(HttpSession session,Integer productId){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(UserDO.getId(),productId,Const.Cart.CHECKED);
    }

    @RequestMapping("un_select")
    @ResponseBody
    public ServerResponse<CartVO> unSelect(HttpSession session,Integer productId){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(UserDO.getId(),productId,Const.Cart.UN_CHECKED);
    }



    @RequestMapping("get_cart_product_count")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession session){
        UserDO UserDO = (UserDO)session.getAttribute(Const.CURRENT_USER);
        if(UserDO ==null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(UserDO.getId());
    }
}
