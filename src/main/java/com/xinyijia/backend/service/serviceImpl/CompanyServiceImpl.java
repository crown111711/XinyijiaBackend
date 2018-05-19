package com.xinyijia.backend.service.serviceImpl;

import com.xinyijia.backend.domain.CompanyInfo;
import com.xinyijia.backend.domain.CompanyInfoExample;
import com.xinyijia.backend.domain.NewsInfo;
import com.xinyijia.backend.domain.NewsInfoExample;
import com.xinyijia.backend.mapper.CompanyInfoMapper;
import com.xinyijia.backend.mapper.NewsInfoMapper;
import com.xinyijia.backend.param.NewsResponse;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.service.CompanyService;
import com.xinyijia.backend.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/19 23:40
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyInfoMapper companyInfoMapper;

    @Autowired
    NewsInfoMapper newsInfoMapper;

    @Autowired
    NewsServiceImpl newsService;

    @Override
    public CompanyInfo getCompany() {
        List<CompanyInfo> infos = companyInfoMapper.selectByExample(new CompanyInfoExample());
        if (CollectionUtils.isEmpty(infos))
            return new CompanyInfo();
        return infos.get(0);
    }

    @Override
    public void updateBasicInfo(CompanyInfo companyInfo) {
        //生日格式调整
        try {
            if (StringUtils.isNotBlank(companyInfo.getCreateTime())) {
                String fdDate = companyInfo.getCreateTime().replace("Z", " UTC");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                Date date = dateFormat.parse(fdDate);
                SimpleDateFormat dfFormat = new SimpleDateFormat("yyyy-MM-dd");
                companyInfo.setCreateTime(dfFormat.format(date));
            }
        } catch (Exception e) {
            log.error("时间转换错误", e);
        }

        if (companyInfo.getId() == null) {
            companyInfoMapper.insert(companyInfo);
        } else {
            companyInfoMapper.updateByPrimaryKey(companyInfo);
        }
    }

    @Override
    public NewsResponse getAbout(String category) {
        NewsInfoExample query = new NewsInfoExample();
        query.createCriteria().andCategoryEqualTo(category);
        List<NewsInfo> newsInfos = newsInfoMapper.selectByExampleWithBLOBs(query);
        if (!CollectionUtils.isEmpty(newsInfos)) {
            return newsService.convert(newsInfos).get(0);
        }
        NewsResponse rep = new NewsResponse();
        rep.setCategory(category);
        return rep;
    }

    @Override
    public NewsResponse getRecruit() {
        return null;
    }
}
