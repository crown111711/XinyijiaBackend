package com.xinyijia.backend.controller;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.domain.BusinessInfo;
import com.xinyijia.backend.domain.ProductInfo;
import com.xinyijia.backend.param.ProductResponse;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "更新商品信息", notes = "")
    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    public BaseResponse updateProduct(@RequestBody ProductInfo productInfo) throws Exception {
        try {
            productService.updateProduct(productInfo);
            BaseResponse response = new BaseResponse();
            return response;
        } catch (Exception e) {
            log.error("更新商品信息失败", e);
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }


    @ApiOperation(value = "获取所有的产品", notes = "")
    @RequestMapping(value = "getAllProducts", method = RequestMethod.GET)
    public BaseResponse getAllProducts() {
        BaseResponse baseResponse = new BaseResponse();
        List<ProductResponse> productResponses = productService.getAllProducts();
        baseResponse.setCode(BusinessResponseCode.SUCCESS);
        baseResponse.setData(productResponses);
        return baseResponse;
    }

    @ApiOperation(value = "推荐商品", notes = "")
    @RequestMapping(value = "recommendProduct", method = RequestMethod.GET)
    public BaseResponse recommendProduct(@RequestParam(name = "accessToken", required = false) String accessToken) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setData(productService.recommendProducts(accessToken));
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "删除产品", notes = "")
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
    public BaseResponse deleteProduct(@RequestParam("id") Integer id) {
        try {
            productService.deleteProduct(id);
            return new BaseResponse(BusinessResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error("删除异常", e);
            return new BaseResponse(BusinessResponseCode.ERROR);
        }

    }

    @ApiOperation(value = "删除业务线", notes = "")
    @RequestMapping(value = "/deleteBusiness", method = RequestMethod.GET)
    public BaseResponse deleteBusiness(@RequestParam("id") Integer id) {
        try {
            int code = productService.deleteBusiness(id);
            BaseResponse baseResponse = new BaseResponse(code);
            if (code == 1) {
                baseResponse.setMsg("该业务线下还有商品，请先处理");
            }
            return baseResponse;
        } catch (Exception e) {
            log.error("删除异常", e);
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "查询产品", notes = "")
    @RequestMapping(value = "searchProducts", method = RequestMethod.GET)
    public BaseResponse searchProducts(@RequestParam("searchParam") String searcParams) {
        // 商品名  商品关键字  商品类型
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<ProductResponse> products = productService.searchProducts(searcParams);
            baseResponse.setCode(BusinessResponseCode.SUCCESS);
            baseResponse.setData(products);
        } catch (Exception e) {
            log.error("查询商品失败", e);
            baseResponse.setCode(BusinessResponseCode.ERROR);
        }
        return baseResponse;
    }


    @ApiOperation(value = "查询产品", notes = "")
    @RequestMapping(value = "/getProductsInBusiness", method = RequestMethod.GET)
    public BaseResponse getProductsInBusiness(@RequestParam(name = "businessName", required = false) String businessName,
                                              @RequestParam(name = "businessId", required = false) Integer businessId,
                                              @RequestParam(name = "productId", required = false) Integer productId) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setData(productService.getProductInBusiness(businessName, businessId, productId));
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }

    }


    @ApiOperation(value = "得到所有的业务线", notes = "")
    @RequestMapping(value = "getAllBusiness", method = RequestMethod.GET)
    public List<BusinessInfo> getAllBusiness() {
        return productService.getAllBusiness();
    }

    @ApiOperation(value = "添加业务线", notes = "")
    @RequestMapping(value = "addBusiness", method = RequestMethod.POST)
    public BaseResponse addBusiness(@RequestBody BusinessInfo businessInfo) throws Exception {

        try {
            BaseResponse response = new BaseResponse();
            Integer id = productService.addBusiness(businessInfo);
            response.setMsg(BusinessResponseCode.OPERATE_SUCCESS_MSG);
            response.setCode(BusinessResponseCode.SUCCESS);
            if (id != null) {
                response.setData(id);
            }
            return response;
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setCode(BusinessResponseCode.ERROR);
            response.setMsg("业务线不能重名");
            return response;
        }
    }

    @ApiOperation(value = "修改业务线", notes = "")
    @RequestMapping(value = "updateBusiness", method = RequestMethod.POST)
    public BaseResponse updateBusiness(@RequestBody BusinessInfo businessInfo) throws Exception {
        BaseResponse baseResponse = new BaseResponse(BusinessResponseCode.SUCCESS, BusinessResponseCode.OPERATE_SUCCESS_MSG);
        productService.updateBusiness(businessInfo);
        return baseResponse;
    }

}
