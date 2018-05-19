package com.xinyijia.backend.controller;

import com.google.common.collect.Lists;
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
import org.springframework.util.CollectionUtils;
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

    @ApiOperation(value = "修改新闻", notes = "")
    @RequestMapping(value = "/updateNews", method = RequestMethod.POST)
    public BaseResponse updateNews(@RequestBody NewsInfo newsInfo) {
        try {
            newsService.updateNews(newsInfo);
            return new BaseResponse(BusinessResponseCode.SUCCESS);
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "获取新闻", notes = "")
    @RequestMapping(value = "/getNews", method = RequestMethod.GET)
    public BaseResponse getNews(@RequestParam(name = "category", required = false) String category,
                                @RequestParam(name = "lastId", required = false) Integer lastId,
                                @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                @RequestParam(name = "direction", required = false) String direction) {
        try {
            BaseResponse baseResponse = new BaseResponse();
            List<NewsResponse> newsResponseList = newsService.getNews(category);
            if (CollectionUtils.isEmpty(newsResponseList)) {
                newsResponseList = Lists.newArrayList();
            }
            baseResponse.setData(newsResponseList);
            baseResponse.setCode(BusinessResponseCode.SUCCESS);
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }


    @ApiOperation(value = "获取新闻", notes = "")
    @RequestMapping(value = "/getNews/{id}", method = RequestMethod.GET)
    public BaseResponse getNewsById(@PathVariable("id") Integer id) {
        try {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(newsService.getNewsById(id));
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
