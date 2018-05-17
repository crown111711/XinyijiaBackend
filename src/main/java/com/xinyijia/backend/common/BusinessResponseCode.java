package com.xinyijia.backend.common;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 15:07
 */
public interface BusinessResponseCode {
    int SUCCESS = 0;

    int ERROR = 1;

    int USER_NOT_LOGIN = 44;

    int CAPTCHA_ERROR = 88;
    int USER_NAME_EXIST = 99;

    int CODE_ALREADY_SEND = 88;
    int CODE_SEND_ERROR = 99;

    String OPERATE_SUCCESS_MSG = "操作成功";

    String ADD_NEWS_SUCCESS =" 添加新闻成功";
}
