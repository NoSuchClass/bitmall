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
    public static final String ACCESSKEYID = "LTAIYlVnOtgszXQB";
    public static final String ACCESSKEYSECRET = "bovtXrcILhjPYgVXfdlS57OtjsA8wh";
    public static final String BUCKETNAME = "bitongyun";
    public static final String FILEHOST = "bitongyun";

    public static interface ProductListOrderBy {
        // 使用set可以提高查询效率，时间复杂度O（1）
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }
    public static interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }
    public interface Cart{
        int CHECKED = 1;//即购物车选中状态
        int UN_CHECKED = 0;//购物车中未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }
    public enum ProductStatusEnum {
        /**
         * 销售状态时的码
         */
        ON_SALE("正在销售", 1);

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        ProductStatusEnum(String value, int code) {
            this.value = value;
            this.code = code;
        }
    }
}

