package com.xinyijia.backend.param;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/19 0:30
 */
@Data
public class BuyCarResponse {
    private Integer id;
    private Integer quantity;
    private Integer price;
    private Integer userId;
    private Integer productId;
    private String productName;
}
