package com.xinyijia.backend.service.serviceImpl;

import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.common.UserCategory;
import com.xinyijia.backend.domain.UserInfo;
import com.xinyijia.backend.domain.UserInfoExample;
import com.xinyijia.backend.mapper.UserInfoMapper;
import com.xinyijia.backend.param.CaptchaCache;
import com.xinyijia.backend.param.TokenCache;
import com.xinyijia.backend.param.request.LoginRequest;
import com.xinyijia.backend.param.request.UserUpdateRequest;
import com.xinyijia.backend.param.response.LoginResponse;
import com.xinyijia.backend.param.response.UserInfoResponse;
import com.xinyijia.backend.service.TokenCacheService;
import com.xinyijia.backend.service.UserService;
import com.xinyijia.backend.utils.MailSenderInfo;
import com.xinyijia.backend.utils.MailUtils;
import com.xinyijia.backend.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 14:04
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TokenCacheService tokenCacheService;

    private static ConcurrentHashMap<String, CaptchaCache> captchaCodeU = new ConcurrentHashMap<>();

    @Override
    public Integer register(LoginRequest request) {
        CaptchaCache captchaCache = captchaCodeU.get(request.getEmail());
        if (captchaCache == null) {
            return BusinessResponseCode.CAPTCHA_ERROR;
        }
        String captchaCode = captchaCache.getCaptchaCode();
        if (!captchaCode.equals(request.getCaptchaCode())) {
            return BusinessResponseCode.CAPTCHA_ERROR;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(request.getUserName());
        userInfo.setPassword(request.getPassword());
        userInfo.setEmail(request.getEmail());
        userInfo.setCreateTime(System.currentTimeMillis());
        try {
            int result = userInfoMapper.insert(userInfo);
            log.info("添加用户 userName:{},result:{}", request.getUserName(), result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return BusinessResponseCode.USER_NAME_EXIST;
        }
        return 0;
    }

    @Override
    public Integer getCaptchaCode(String email) {
        try {

            CaptchaCache captchaCacheOld = captchaCodeU.get(email);
            if (captchaCacheOld != null
                    && (System.currentTimeMillis() - captchaCacheOld.getCreateTime()) < 60 * 1000) {
                return BusinessResponseCode.CODE_ALREADY_SEND;
            }
            MailSenderInfo mailSenderInfo = new MailSenderInfo();
            mailSenderInfo.setToAddress(new String[]{email});
            mailSenderInfo.setSubject("新亿嘉注册验证码");
            String code = RandomUtil.random(8);
            mailSenderInfo.setContent("您的验证码为:" + code);
            CaptchaCache captchaCacheUpdate = new CaptchaCache();
            captchaCacheUpdate.setEmail(email);
            captchaCacheUpdate.setCaptchaCode(code);
            captchaCacheUpdate.setCreateTime(System.currentTimeMillis());
            captchaCodeU.put(email, captchaCacheUpdate);
            MailUtils.sendHtmlMail(mailSenderInfo);
        } catch (Exception e) {
            return BusinessResponseCode.CODE_SEND_ERROR;
        }
        return BusinessResponseCode.SUCCESS;
    }

    @Override
    public UserInfo findUserByUserName(String userName) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andUserNameEqualTo(userName);
        List<UserInfo> users = userInfoMapper.selectByExample(userInfoExample);
        if (org.springframework.util.CollectionUtils.isEmpty(users)) {
            return null;
        }
        users.sort(Comparator.comparing(UserInfo::getCreateTime).reversed());
        return users.get(0);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        UserInfo userInfo = findUserByUserName(userName);
        if (userInfo == null) {
            return LoginResponse
                    .builder()
                    .status(false)
                    .userName(userName)
                    .build();
        }
        String tokenId = RandomUtil.getGlobalToken();
        TokenCache tokenCache = new TokenCache();
        tokenCache.setUserName(userName);
        tokenCache.setEmail(userInfo.getEmail());
        tokenCache.setUid(userInfo.getId());
        tokenCache.setAccessToken(tokenId);
        tokenCache.setSessionId(loginRequest.getSessionId());
        tokenCacheService.putCache(tokenId, tokenCache);
        LoginResponse.LoginResponseBuilder builder = LoginResponse.builder()
                .status(true)
                .userName(userName)
                .uid(userInfo.getId())
                .accessToken(tokenId);
        if (UserCategory.ADMIN.name().equals(userInfo.getCategory())) {
            builder.isAdmin(true);
        }
        return builder.build();
    }

    @Override
    public boolean isLogin(String accessToken) {
        TokenCache tokenCache = tokenCacheService.getCache(accessToken);
        if (tokenCache == null) {
            return false;
        }
        return true;
    }

    @Override
    public UserInfoResponse getUserInfo(String accessToken) {
        UserInfoResponse response = new UserInfoResponse();
        TokenCache tokenCache = tokenCacheService.getCache(accessToken);
        if (tokenCache == null) {
            response.setCode(BusinessResponseCode.USER_NOT_LOGIN);
            return response;
        }
        String userName = tokenCache.getUserName();
        int uid = tokenCache.getUid();
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(uid);
        BeanUtils.copyProperties(userInfo, response);
        return response;
    }

    @Override
    public Integer updateUserInfo(UserUpdateRequest userUpdateRequest) {
        try {
            if (userUpdateRequest == null || StringUtils.isEmpty(userUpdateRequest.getAccessToken())) {
                return BusinessResponseCode.USER_NOT_LOGIN;
            }
            TokenCache tokenCache = tokenCacheService.getCache(userUpdateRequest.getAccessToken());
            if (tokenCache == null || !Objects.equals(userUpdateRequest.getUserName(), userUpdateRequest.getUserName())) {
                return BusinessResponseCode.USER_NOT_LOGIN;
            }

            int uid = tokenCache.getUid();
            UserInfo update = new UserInfo();
            BeanUtils.copyProperties(userUpdateRequest, update);

            //生日格式调整
            String fdDate = update.getBirthday().replace("Z", " UTC");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = dateFormat.parse(fdDate);
            SimpleDateFormat dfFormat = new SimpleDateFormat("yyyy-MM-dd");
            update.setBirthday(dfFormat.format(date));

            update.setId(uid);
            int result = userInfoMapper.updateBasicUserInfo(update);
            return BusinessResponseCode.SUCCESS;
        } catch (Exception e) {
            log.error("更新数据异常", e);
            return BusinessResponseCode.ERROR;
        }
    }
}
