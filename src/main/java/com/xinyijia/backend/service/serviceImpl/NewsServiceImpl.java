package com.xinyijia.backend.service.serviceImpl;

import com.google.common.collect.Lists;
import com.xinyijia.backend.domain.NewsInfo;
import com.xinyijia.backend.domain.NewsInfoExample;
import com.xinyijia.backend.mapper.NewsInfoMapper;
import com.xinyijia.backend.param.NewsResponse;
import com.xinyijia.backend.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/17 20:30
 */
@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsInfoMapper newsInfoMapper;

    @Override
    public void addNews(NewsInfo newsInfo) {
        newsInfo.setPublishTime(System.currentTimeMillis());
        newsInfoMapper.insert(newsInfo);
    }

    @Override
    public List<NewsResponse> getNews(String category) {
        List<NewsInfo> newsInfos = null;
        if (StringUtils.isBlank(category)) {
            newsInfos = newsInfoMapper.selectByExampleWithBLOBs(new NewsInfoExample());
        } else {
            NewsInfoExample newsInfoExample = new NewsInfoExample();
            newsInfoExample.createCriteria().andCategoryEqualTo(category);
            newsInfos = newsInfoMapper.selectByExampleWithBLOBs(newsInfoExample);
        }
        if (!CollectionUtils.isEmpty(newsInfos)) {
            return convert(newsInfos);
        }
        return null;
    }

    @Override
    public void deleteNews(Integer id) {
        newsInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateNews(NewsInfo newsInfo) {
        newsInfoMapper.updateByPrimaryKeyWithBLOBs(newsInfo);
    }

    @Override
    public NewsResponse getNewsById(Integer id) {
        NewsInfo newsInfo = newsInfoMapper.selectByPrimaryKey(id);
        if (newsInfo == null) {
            return null;
        }
        return convert(Lists.newArrayList(newsInfo)).get(0);

    }

    private List<NewsResponse> convert(List<NewsInfo> newsInfos) {
        return newsInfos.stream().map(
                info -> {
                    NewsResponse response = new NewsResponse();
                    BeanUtils.copyProperties(info, response);
                    if (StringUtils.isNotBlank(info.getTitleImage())) {
                        response.setImageUrl("http://localhost:8090/xyj/api/attachment/showImage/" + info.getTitleImage());
                    }

                    if (info.getPublishTime() != null) {
                        response.setPublishTime(LocalDateTime.fromDateFields(new Date(info.getPublishTime())).toString(DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss")));
                    }
                    return response;
                }

        ).collect(Collectors.toList());
    }

}
