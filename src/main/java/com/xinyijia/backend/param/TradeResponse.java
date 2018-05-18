package com.xinyijia.backend.param;

import com.xinyijia.backend.domain.ProductInfo;
import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/18 21:12
 */
@Data
public class TradeResponse {

    private Integer id;

    private Integer userId;

    private String userName;

    private Integer productId;

    private ProductInfo productInfo;

    private Integer quantity;

    private Integer price;

    private Long createtime;

    private String tradeTime;

    private Long updatetime;
}
