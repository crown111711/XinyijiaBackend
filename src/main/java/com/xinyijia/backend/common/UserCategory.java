package com.xinyijia.backend.common;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/17 16:55
 */
public enum UserCategory {
    ADMIN(0, "管理员"),
    NORMAL(1, "普通会员");
    String desc;
    int code;

    UserCategory(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
