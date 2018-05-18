package com.xinyijia.backend.controller;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.domain.BuyCar;
import com.xinyijia.backend.param.request.LoginRequest;
import com.xinyijia.backend.param.request.RechargeRequest;
import com.xinyijia.backend.param.request.TradeRequest;
import com.xinyijia.backend.param.request.UserUpdateRequest;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.param.response.CaptchaResponse;
import com.xinyijia.backend.param.response.LoginResponse;
import com.xinyijia.backend.param.response.UserInfoResponse;
import com.xinyijia.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

/**
 * Created by tanjia on 2018/5/12.
 */
@Api("用户管理相关api")
@RestController
@RequestMapping(value = BaseConsant.BASE_PATH + "/users")
@CrossOrigin("*")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登陆", notes = "")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        loginRequest.setSessionId(httpSession.getId());
        return userService.login(loginRequest);
    }

    @ApiOperation(value = "注册", notes = "")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public CaptchaResponse register(@RequestBody LoginRequest loginRequest) {
        log.info("userName:{},password:{},email:{}", loginRequest.getUserName(),
                loginRequest.getPassword(), loginRequest.getEmail());
        int result = userService.register(loginRequest);
        CaptchaResponse captchaResponse = new CaptchaResponse();
        captchaResponse.setCode(result);
        return captchaResponse;
    }

    @ApiOperation(value = "验证是否已经登陆", notes = "")
    @RequestMapping(value = "isLogin", method = RequestMethod.GET)
    public boolean isLogin(@RequestParam(name = "accessToken", required = false) String accessToken) {
        return userService.isLogin(accessToken);
    }


    @ApiOperation(value = "验证码生成并发送", notes = "")
    @RequestMapping(value = "getCaptcha", method = RequestMethod.POST)
    public CaptchaResponse getCaptcha(@RequestBody LoginRequest loginRequest) {
        CaptchaResponse captchaResponse = new CaptchaResponse();
        try {
            int code = userService.getCaptchaCode(loginRequest.getEmail());
            captchaResponse.setCode(code);
        } catch (Exception e) {
            captchaResponse.setCode(BusinessResponseCode.CODE_SEND_ERROR);
        }
        return captchaResponse;
    }

    @ApiOperation(value = "获取用户信息", notes = "")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    @NotNull
    public UserInfoResponse getUserInfo(@RequestParam("accessToken") String accessToken) {
        try {
            UserInfoResponse userInfoResponse = userService.getUserInfo(accessToken);
            if (userInfoResponse == null) {
                UserInfoResponse response = new UserInfoResponse();
                response.setCode(BusinessResponseCode.ERROR);
                return response;
            }
            userInfoResponse.setCode(BusinessResponseCode.SUCCESS);
            return userInfoResponse;
        } catch (Exception e) {
            UserInfoResponse response = new UserInfoResponse();
            response.setCode(BusinessResponseCode.ERROR);
            return response;
        }
    }

    @ApiOperation(value = "修改用户信息", notes = "")
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    @NotNull
    public BaseResponse updateUserInfo(@RequestBody UserUpdateRequest request) {
        try {
            Integer code = userService.updateUserInfo(request);
            BaseResponse response = new BaseResponse();
            response.setCode(code);
            return response;
        } catch (Exception e) {
            log.error("更新用户信息异常", e);
            return fail(new BaseResponse());
        }
    }

    @ApiOperation(value = "充值", notes = "")
    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    @NotNull
    public BaseResponse recharge(@RequestBody RechargeRequest rechargeRequest) {

        BaseResponse baseResponse = BaseResponse.success();
        userService.recharge(rechargeRequest);
        return baseResponse;
    }

    @ApiOperation(value = "得到用户的交易信息", notes = "")
    @RequestMapping(value = "getUserTrades", method = RequestMethod.GET)
    @NotNull
    public BaseResponse getUserTrades(@RequestParam("accessToken") String accessToken) {

        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setData(userService.getTradesInfos(accessToken));
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "得到用户的购物车信息", notes = "")
    @RequestMapping(value = "getBuyCars", method = RequestMethod.GET)
    @NotNull
    public BaseResponse getBuyCars(@RequestParam("accessToken") String accessToken) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setData(userService.getBuyCars(accessToken));
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "添加到购物车", notes = "")
    @RequestMapping(value = "/addBuyCar", method = RequestMethod.POST)
    @NotNull
    public BaseResponse addBuyCar(@RequestBody BuyCar buyCar) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            userService.addBuyCard(buyCar);
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }


    @ApiOperation(value = "删除购物车", notes = "")
    @RequestMapping(value = "/deleteBuyCar/{id}", method = RequestMethod.GET)
    @NotNull
    public BaseResponse deleteBuyCar(@PathVariable("id") Integer id) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            userService.deleteBuyCa(id);
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "更新购物车", notes = "")
    @RequestMapping(value = "/updateBuyCar", method = RequestMethod.POST)
    @NotNull
    public BaseResponse updateBuyCar(@RequestBody BuyCar buyCar) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            userService.updateBuyCar(buyCar);
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }


    @ApiOperation(value = "购买商品", notes = "")
    @RequestMapping(value = "/buyProduct", method = RequestMethod.POST)
    public BaseResponse buyProduct(@RequestBody TradeRequest tradeRequest) {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setCode(userService.buyProduct(tradeRequest));
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }

    }

    private BaseResponse fail(BaseResponse baseResponse) {
        baseResponse.setCode(BusinessResponseCode.ERROR);
        return baseResponse;
    }

    private BaseResponse success(BaseResponse baseResponse) {
        baseResponse.setCode(BusinessResponseCode.SUCCESS);
        return baseResponse;
    }
}
