package com.xinyijia.backend.controller;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.domain.BusinessInfo;
import com.xinyijia.backend.domain.ProductInfo;
import com.xinyijia.backend.param.request.LoginRequest;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.param.response.LoginResponse;
import com.xinyijia.backend.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/16 11:27
 */
@Api("产品管理相关Api")
@RestController
@RequestMapping(value = BaseConsant.BASE_PATH + "/products")
@CrossOrigin("*")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "添加商品", notes = "")
    @RequestMapping(value = "addProducts", method = RequestMethod.POST)
    public BaseResponse addProducts(@RequestBody ProductInfo productInfo) throws Exception {
        productService.addProduct(productInfo);
        BaseResponse response = new BaseResponse();
        response.setMsg(BaseConsant.ADD_PRODUCT_SUCCESS);
        return response;
    }

    @ApiOperation(value = "得到所有的业务线", notes = "")
    @RequestMapping(value = "getAllBusiness", method = RequestMethod.GET)
    public List<BusinessInfo> getAllBusiness() {
        return productService.getAllBusiness();
    }

    @ApiOperation(value = "添加业务线", notes = "")
    @RequestMapping(value = "addBusiness", method = RequestMethod.POST)
    public BaseResponse addBusiness(@RequestBody BusinessInfo businessInfo) {

        try {
            BaseResponse response = new BaseResponse();
            productService.addBusiness(businessInfo);
            response.setMsg(BusinessResponseCode.OPERATE_SUCCESS_MSG);
            return response;
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setMsg("业务线不能重名");
            return response;
        }
    }


}
