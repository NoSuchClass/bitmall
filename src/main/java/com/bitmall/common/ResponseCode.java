package com.bitmall.common;

/**
 * @author liuyuehe
 * @date 2019/6/9 10:31
 */
public enum ResponseCode {
    /**
     * SUCCESS：成功
     * ERROR：错误
     * NEED_LOGIN：需要登录
     * ILLEGAL_ARGUMENT：非法参数
     */
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
