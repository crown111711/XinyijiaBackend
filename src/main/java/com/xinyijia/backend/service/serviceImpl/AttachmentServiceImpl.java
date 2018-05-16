package com.xinyijia.backend.service.serviceImpl;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.domain.AttachmentInfo;
import com.xinyijia.backend.domain.AttachmentInfoExample;
import com.xinyijia.backend.domain.UserInfo;
import com.xinyijia.backend.mapper.AttachmentInfoMapper;
import com.xinyijia.backend.mapper.ProductInfoMapper;
import com.xinyijia.backend.mapper.UserInfoMapper;
import com.xinyijia.backend.param.AttachmentInfluence;
import com.xinyijia.backend.param.TokenCache;
import com.xinyijia.backend.service.AttachmentService;
import com.xinyijia.backend.service.TokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/16 17:43
 */
@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentInfoMapper attachmentInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private TokenCacheService tokenCacheService;

    @Override
    public void saveAttachment(AttachmentInfo attachment) {
        attachmentInfoMapper.insert(attachment);
    }

    @Override
    public AttachmentInfo getOneByName(String fileName) {
        AttachmentInfoExample attachmentInfoExample = new AttachmentInfoExample();
        attachmentInfoExample.createCriteria().andFileNameEqualTo(fileName);
        List<AttachmentInfo> attachmentInfoList = attachmentInfoMapper.selectByExample(attachmentInfoExample);
        if (CollectionUtils.isEmpty(attachmentInfoList)) {
            return null;
        }
        return attachmentInfoList.get(0);
    }

    @Override
    public void catToRelative(AttachmentInfluence attachmentInfluence) {
        if (StringUtils.isEmpty(attachmentInfluence.getFileName())) {
            return;
        }
        switch (attachmentInfluence.getType()) {
            case BaseConsant.USER_ICON_IMAGE:
                TokenCache tokenCache = tokenCacheService.getCache(attachmentInfluence.getAccessToken());
                if (tokenCache == null) {
                    return;
                }
                int uid = tokenCache.getUid();
                UserInfo update = new UserInfo();
                update.setId(uid);
                update.setImageIcon(attachmentInfluence.getFileName());
                userInfoMapper.updateByPrimaryKeySelective(update);
                break;

        }


    }
}
