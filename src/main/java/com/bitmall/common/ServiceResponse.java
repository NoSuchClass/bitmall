package com.bitmall.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author liuyuehe
 * @date 2019/6/9 10:23
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ServiceResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    /**
     * 私有化所有的构造函数，防止外部直接new
     *
     * @param status
     */
    private ServiceResponse(Integer status) {
        this.status = status;
    }

    private ServiceResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServiceResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServiceResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ServiceResponse<T> createBySuccess() {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createBySuccessMessage(String msg) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServiceResponse<T> createBySuccess(T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    /**
     * 为了在传入参数时，满足需要把String类型的数据视作data的情况，需要添加一个方法
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createBySuccess(String msg, T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServiceResponse<T> createByError() {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServiceResponse<T> createByErrorByMessage(String erroMessage) {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), erroMessage);
    }

    public static <T> ServiceResponse<T> createByErrorCodeMessage(int erroCode, String erroMessage) {
        return new ServiceResponse<T>(erroCode, erroMessage);
    }

    @JsonIgnore
    public Boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
