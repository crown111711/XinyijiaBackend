package com.xinyijia.backend.service.serviceImpl;

import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.common.UserCategory;
import com.xinyijia.backend.domain.*;
import com.xinyijia.backend.mapper.BuyCarMapper;
import com.xinyijia.backend.mapper.ProductInfoMapper;
import com.xinyijia.backend.mapper.TradeInfoMapper;
import com.xinyijia.backend.mapper.UserInfoMapper;
import com.xinyijia.backend.param.*;
import com.xinyijia.backend.param.request.LoginRequest;
import com.xinyijia.backend.param.request.RechargeRequest;
import com.xinyijia.backend.param.request.TradeRequest;
import com.xinyijia.backend.param.request.UserUpdateRequest;
import com.xinyijia.backend.param.response.LoginResponse;
import com.xinyijia.backend.param.response.UserInfoResponse;
import com.xinyijia.backend.service.TokenCacheService;
import com.xinyijia.backend.service.UserService;
import com.xinyijia.backend.utils.MailSenderInfo;
import com.xinyijia.backend.utils.MailUtils;
import com.xinyijia.backend.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    @Autowired
    private TradeInfoMapper tradeInfoMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private BuyCarMapper buyCarMapper;


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
        if (UserCategory.ADMIN.name().equalsIgnoreCase(userInfo.getCategory())) {
            builder.isAdmin(true);
        } else {
            builder.isAdmin(false);
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

    @Override
    public int recharge(RechargeRequest rechargeRequest) {
        TokenCache tokenCache = tokenCacheService.getCache(rechargeRequest.getAccessToken());
        if (tokenCache == null) {
            return BusinessResponseCode.USER_NOT_LOGIN;
        }
        int uid = tokenCache.getUid();
        UserInfo update = new UserInfo();
        update.setBalance(rechargeRequest.getBalance());
        update.setId(uid);
        userInfoMapper.updateByPrimaryKeySelective(update);
        return 0;
    }

    @Override
    public List<TradeResponse> getTradesInfos(String accessToken) {
        TokenCache tokenCache = tokenCacheService.getCache(accessToken);
        if (tokenCache == null) {
            return null;
        }
        int uid = tokenCache.getUid();
        TradeInfoExample tradeInfoExample = new TradeInfoExample();
        tradeInfoExample.createCriteria().andUserIdEqualTo(uid);
        return convertTrade(tradeInfoMapper.selectByExample(tradeInfoExample), tokenCache);
    }

    @Override
    @Transactional
    public int buyProduct(TradeRequest tradeRequest) {
        if (tradeRequest == null || CollectionUtils.isEmpty(tradeRequest.getTrades())) {
            return 0;
        }

        TokenCache tokenCache = tokenCacheService.getCache(tradeRequest.getAccessToken());
        if (tokenCache == null) {
            return BusinessResponseCode.USER_NOT_LOGIN;
        }

        int count = 0;
        for (TradeSubRequest request : tradeRequest.getTrades()) {
            count += request.getQuantity() * request.getPrice();
        }
        //账户的分布式管理 考虑并发、事务等相关问题，这里暂时不考虑

        int uid = tokenCache.getUid();
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(uid);
        if (userInfo.getBalance() == null || userInfo.getBalance() < count) {
            //账户余额不够
            return 1;
        }


        for (TradeSubRequest request : tradeRequest.getTrades()) {
            //生成订单
            TradeInfo tradeInfo = new TradeInfo();
            BeanUtils.copyProperties(request, tradeInfo);
            tradeInfo.setId(null);
            tradeInfo.setUserId(uid);
            tradeInfo.setCreatetime(System.currentTimeMillis());
            tradeInfoMapper.insert(tradeInfo);

            //扣减库存
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(request.getProductId());
            ProductInfo updateQuantity = new ProductInfo();
            updateQuantity.setId(request.getProductId());
            updateQuantity.setQuantity(productInfo.getQuantity() - request.getQuantity());
            updateQuantity.setSellNum(productInfo.getSellNum() + updateQuantity.getSellNum());
            productInfoMapper.updateByPrimaryKeySelective(updateQuantity);

            //删减购物车信息
            buyCarMapper.deleteByPrimaryKey(request.getId());

        }

        //用户余额减少
        UserInfo updateBalance = new UserInfo();
        updateBalance.setId(uid);
        updateBalance.setBalance(userInfo.getBalance() - count);
        userInfoMapper.updateByPrimaryKeySelective(updateBalance);

        //发邮件通知
        //TODO

        return 0;
    }

    @Override
    public List<BuyCarResponse> getBuyCars(String accessToken) {
        TokenCache tokenCache = tokenCacheService.getCache(accessToken);
        if (tokenCache == null) {
            return null;
        }
        int uid = tokenCache.getUid();
        BuyCarExample query = new BuyCarExample();
        query.createCriteria().andUserIdEqualTo(uid);
        List<BuyCar> buyCars = buyCarMapper.selectByExample(query);
        return convertBuyCars(buyCars);
    }

    @Override
    public void addBuyCard(BuyCar buyCar) {
        buyCarMapper.insert(buyCar);
    }

    @Override
    public void deleteBuyCa(Integer id) {
        buyCarMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateBuyCar(BuyCar buyCar) {
        buyCarMapper.updateByPrimaryKey(buyCar);
    }

    private List<BuyCarResponse> convertBuyCars(List<BuyCar> buyCars) {
        if (CollectionUtils.isEmpty(buyCars)) {
            return null;
        }
        return buyCars.stream().map(
                buyCar -> {
                    BuyCarResponse buyCarResponse = new BuyCarResponse();
                    BeanUtils.copyProperties(buyCar, buyCarResponse);
                    buyCarResponse.setProductName(productInfoMapper.selectByPrimaryKey(buyCar.getProductId()).getProductName());
                    return buyCarResponse;
                }
        ).collect(Collectors.toList());
    }


    private List<TradeResponse> convertTrade(List<TradeInfo> tradeInfos, TokenCache tokenCache) {
        if (CollectionUtils.isEmpty(tradeInfos)) {
            return null;
        }
        return tradeInfos.stream().map(
                tradeInfo -> {
                    TradeResponse tradeResponse = new TradeResponse();
                    BeanUtils.copyProperties(tradeInfo, tradeResponse);
                    if (tradeInfo.getProductId() != null) {
                        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(tradeInfo.getProductId());
                        tradeResponse.setProductInfo(productInfo);
                    }
                    if (tokenCache != null) {
                        tradeResponse.setUserName(tokenCache.getUserName());
                    }
                    return tradeResponse;
                }
        ).collect(Collectors.toList());
    }
}
