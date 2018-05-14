package com.xinyijia.backend.param;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 17:15
 */
@Data
public class CaptchaCache {
    private String email;
    private String captchaCode;
    private long createTime;
}
