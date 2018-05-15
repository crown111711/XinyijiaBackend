package com.xinyijia.backend.param.response;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/15 14:21
 */
@Data
public class UserInfoResponse extends BaseResponse {
    private String userName;
    private String telNum;
    private String idCard;
    private String email;
    private String sex;
    private String address;
    private String birthday;
    private String imageIcon;
}
