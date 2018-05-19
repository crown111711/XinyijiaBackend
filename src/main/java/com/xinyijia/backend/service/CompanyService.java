package com.xinyijia.backend.service;

import com.xinyijia.backend.domain.CompanyInfo;
import com.xinyijia.backend.param.NewsResponse;
import org.springframework.stereotype.Service;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/19 23:40
 */
@Service
public interface CompanyService {
    CompanyInfo getCompany();
    void updateBasicInfo(CompanyInfo companyInfo);
    NewsResponse getAbout(String category);
    NewsResponse getRecruit();
}
