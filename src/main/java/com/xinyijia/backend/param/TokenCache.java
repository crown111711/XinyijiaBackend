package com.xinyijia.backend.param;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 19:51
 */
@Data
public class TokenCache {
    private String accessToken;
    private String userName;
    private Integer uid;
    private String email;
    private String sessionId;
}
