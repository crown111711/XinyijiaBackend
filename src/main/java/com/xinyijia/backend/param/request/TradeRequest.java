package com.xinyijia.backend.param.request;

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

    @Data
    public class TradeSubRequest{
        private int id;
        private String productName;
        private Integer quantity;
        private Integer price;
        private Integer productId;
    }

}
