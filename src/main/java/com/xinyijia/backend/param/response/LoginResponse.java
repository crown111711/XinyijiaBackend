package com.xinyijia.backend.param.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 12:15
 */
@Data
@Builder
public class LoginResponse extends BaseResponse {
    private String userName;
    private String accessToken;
    private Integer uid;
    private boolean status;
}
