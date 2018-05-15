package com.xinyijia.backend.service;

import com.xinyijia.backend.domain.UserInfo;
import com.xinyijia.backend.param.request.LoginRequest;
import com.xinyijia.backend.param.request.UserUpdateRequest;
import com.xinyijia.backend.param.response.LoginResponse;
import com.xinyijia.backend.param.response.UserInfoResponse;
import org.springframework.stereotype.Service;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 14:03
 */
@Service
public interface UserService {

    Integer register(LoginRequest request);

    Integer getCaptchaCode(String email);

    UserInfo findUserByUserName(String userName);

    LoginResponse login(LoginRequest loginRequest);

    boolean isLogin(String accessToken);

    UserInfoResponse getUserInfo(String accessToken);

    Integer updateUserInfo(UserUpdateRequest userUpdateRequest);
}
