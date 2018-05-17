package com.xinyijia.backend.service;

import com.xinyijia.backend.domain.NewsInfo;
import com.xinyijia.backend.param.NewsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/17 20:29
 */
@Service
public interface NewsService {
    void addNews(NewsInfo newsInfo);
    List<NewsResponse> getNews(String category);
    void deleteNews(Integer id);
}
