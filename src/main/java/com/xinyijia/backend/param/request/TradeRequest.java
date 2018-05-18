package com.xinyijia.backend.param.request;

import com.xinyijia.backend.param.TradeSubRequest;
import lombok.Data;

import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/18 22:25
 */
@Data
public class TradeRequest {
    private String userName;
    private String accessToken;
    List<TradeSubRequest> trades;



}
