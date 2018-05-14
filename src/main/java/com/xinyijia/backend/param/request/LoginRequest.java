package com.xinyijia.backend.param.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/12 19:31
 */
@Data
@ApiModel
public class LoginRequest {
    private String userName;
    private String password;
    private String email;
    private String captchaCode;
    private String sessionId;
}
