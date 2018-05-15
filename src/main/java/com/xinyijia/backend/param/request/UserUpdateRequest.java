package com.xinyijia.backend.param.request;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/15 19:25
 */
@Data
public class UserUpdateRequest {
    private String accessToken;
    private String userName;
    private String telNum;
    private String idCard;
    private String email;
    private String sex;
    private String address;
    private String birthday;
    private String imageIcon;
}
