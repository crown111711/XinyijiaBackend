package com.xinyijia.backend.controller;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.param.request.LoginRequest;
import com.xinyijia.backend.param.request.UserUpdateRequest;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.param.response.CaptchaResponse;
import com.xinyijia.backend.param.response.LoginResponse;
import com.xinyijia.backend.param.response.UserInfoResponse;
import com.xinyijia.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.Request;
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

    private BaseResponse fail(BaseResponse baseResponse) {
        baseResponse.setCode(BusinessResponseCode.ERROR);
        return baseResponse;
    }

    private BaseResponse success(BaseResponse baseResponse) {
        baseResponse.setCode(BusinessResponseCode.SUCCESS);
        return baseResponse;
    }
}
