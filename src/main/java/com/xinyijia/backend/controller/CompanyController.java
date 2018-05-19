package com.xinyijia.backend.controller;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.domain.CompanyInfo;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/19 23:34
 */
@Api("公司管理Api")
@RestController
@RequestMapping(value = BaseConsant.BASE_PATH + "/company")
@CrossOrigin("*")
@Slf4j
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @ApiOperation(value = "获取公司信息", notes = "")
    @RequestMapping(value = "/getCompany", method = RequestMethod.GET)
    @NotNull
    public BaseResponse getCompany() {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setData(companyService.getCompany());
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "更新公司基础信息", notes = "")
    @RequestMapping(value = "/updateBasicInfo", method = RequestMethod.POST)
    @NotNull
    public BaseResponse updateBasicInfo(@RequestBody CompanyInfo companyInfo) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            companyService.updateBasicInfo(companyInfo);
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "得到about", notes = "")
    @RequestMapping(value = "/getAbout", method = RequestMethod.GET)
    @NotNull
    public BaseResponse getAbout(@RequestParam("category") String category) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setData(companyService.getAbout(category));
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }

    }
}
