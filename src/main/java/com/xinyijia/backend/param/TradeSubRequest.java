package com.xinyijia.backend.param;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/19 1:43
 */
@Data
public class TradeSubRequest{
    private int id;
    private String productName;
    private Integer quantity;
    private Integer price;
    private Integer productId;
}
