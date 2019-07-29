package com.oecoo.product.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by gf on 2018/4/26.
 */
public class Const {

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc", "price_desc");
    }

    public enum ProductStatusEnum {

        ON_SALE(1, "在线");

        private int code;
        private String value;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    public interface CategoryStatus {
        int ON = 1;//可用
        int LOWER = 0;//废弃
    }

}
