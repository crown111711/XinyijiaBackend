package com.xinyijia.backend.param.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 15:09
 */
@Data
public class BaseResponse<T> {
    public Integer code;
    public String msg;
    public T data;

    public BaseResponse() {

    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public BaseResponse(Integer code) {
        this.code = code;
    }

    public BaseResponse(Integer code, String mag) {
        this.code = code;
        this.msg = msg;
    }
}
