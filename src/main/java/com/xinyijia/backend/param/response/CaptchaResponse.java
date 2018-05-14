package com.xinyijia.backend.param.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 15:27
 */
@Data
public class CaptchaResponse extends BaseResponse {
    private String captchaCode;
}
