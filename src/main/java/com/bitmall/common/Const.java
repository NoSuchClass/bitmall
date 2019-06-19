package com.bitmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author liuyuehe
 * @date 2019/6/9 13:32
 */
public class Const {
    public final static String CURRENT_USER = "currentUser";

    public final static String USERNAME = "username";
    public final static String EMAIL = "email";
    public static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    public static final String BUCKETNAME = "bitongyun";
    public static final String ACCESSKEYID = "LTAIYlVnOtgszXQB";
    public static final String ACCESSKEYSECRET = "bovtXrcILhjPYgVXfdlS57OtjsA8wh";
    public static final String FILEHOST = "bitongyun";
    public static final String URLPREFIX = "https://" + BUCKETNAME + "." + ENDPOINT + "/";

    public enum ProductStatusEnum {
        /**
         * 销售状态时的码
         */
        ON_SALE("正在销售", 1);

        private String value;
        private int code;

        ProductStatusEnum(String value, int code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStatusEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");


        private String value;
        private int code;
        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("未找到对应枚举");
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PayPlatformEnum {
        ALIPAY(1, "支付宝");

        private String value;
        private int code;
        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PaymentTypeEnum {
        ONLINE_PAY(1, "在线支付");

        private String value;
        private int code;
        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("未找到对应枚举");
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

    }

    public static interface ProductListOrderBy {
        // 使用set可以提高查询效率，时间复杂度O（1）
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public static interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }


    public interface Cart {
        int CHECKED = 1;//即购物车选中状态
        int UN_CHECKED = 0;//购物车中未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }
}

