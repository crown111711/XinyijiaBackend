package com.xinyijia.backend.param;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/17 21:20
 */
@Data
public class NewsResponse {

    private Integer id;

    private String type;

    private String category;

    private String title;

    private String titleImage;

    private String author;

    private String publishTime;

    private String content;

    private String imageUrl;
}
