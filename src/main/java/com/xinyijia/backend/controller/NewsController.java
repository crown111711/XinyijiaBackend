package com.xinyijia.backend.controller;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.domain.NewsInfo;
import com.xinyijia.backend.param.NewsResponse;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/17 20:29
 */
@Api("新闻管理相关Api")
@RestController
@RequestMapping(value = BaseConsant.BASE_PATH + "/news")
@CrossOrigin("*")
@Slf4j
public class NewsController {

    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "添加新闻", notes = "")
    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    public BaseResponse addNews(@RequestBody NewsInfo newsInfo) throws Exception {

        BaseResponse response = new BaseResponse();
        newsService.addNews(newsInfo);
        response.setMsg(BusinessResponseCode.ADD_NEWS_SUCCESS);
        return response;
    }

    @ApiOperation(value = "添加新闻", notes = "")
    @RequestMapping(value = "/getNews", method = RequestMethod.GET)
    public BaseResponse getNews(@RequestParam(name = "category", required = false) String category) {
        try {
            BaseResponse baseResponse = new BaseResponse();
            List<NewsResponse> newsResponseList = newsService.getNews(category);
            baseResponse.setData(newsResponseList);
            baseResponse.setCode(BusinessResponseCode.SUCCESS);
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "删除新闻", notes = "")
    @RequestMapping(value = "/deleteNews", method = RequestMethod.GET)
    public BaseResponse deleteNews(@RequestParam("id") Integer id) {
        try {
            newsService.deleteNews(id);
            return new BaseResponse(BusinessResponseCode.SUCCESS);
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }


}
