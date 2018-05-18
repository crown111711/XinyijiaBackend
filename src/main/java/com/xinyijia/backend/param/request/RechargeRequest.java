package com.xinyijia.backend.param.request;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/18 20:34
 */
@Data
public class RechargeRequest {
    private String accessToken;
    private Long balance;
}
