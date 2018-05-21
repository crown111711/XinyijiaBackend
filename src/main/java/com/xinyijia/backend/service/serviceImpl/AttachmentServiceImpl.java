package com.xinyijia.backend.service.serviceImpl;

import com.google.common.collect.Lists;
import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.domain.AttachmentInfo;
import com.xinyijia.backend.domain.AttachmentInfoExample;
import com.xinyijia.backend.domain.UserInfo;
import com.xinyijia.backend.mapper.AttachmentInfoMapper;
import com.xinyijia.backend.mapper.ProductInfoMapper;
import com.xinyijia.backend.mapper.UserInfoMapper;
import com.xinyijia.backend.param.AttachmentFile;
import com.xinyijia.backend.param.AttachmentInfluence;
import com.xinyijia.backend.param.TokenCache;
import com.xinyijia.backend.service.AttachmentService;
import com.xinyijia.backend.service.TokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        attachment.setCreateTime(System.currentTimeMillis());
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

    @Override
    public List<AttachmentFile> getDownFile() {
        AttachmentInfoExample query = new AttachmentInfoExample();
        query.createCriteria().andCategoryEqualTo(BaseConsant.DOWN_FILE);
        List<AttachmentInfo> infos = attachmentInfoMapper.selectByExample(query);
        if (CollectionUtils.isEmpty(infos)) {
            return Lists.newArrayList();
        }
        return infos.stream().map(info -> {
            AttachmentFile attachmentFile = new AttachmentFile();
            BeanUtils.copyProperties(info, attachmentFile);
            if (StringUtils.isEmpty(attachmentFile.getOldName())) {
                attachmentFile.setOldName(attachmentFile.getFileName());
            }
            if(info.getCreateTime() != null){
                attachmentFile.setCreateTime(LocalDateTime.fromDateFields(new Date(info.getCreateTime())).toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
            }
            attachmentFile.setFileUrl(BaseConsant.SHOW_IMAGE+ info.getFileName());
            return attachmentFile;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteAttachment(String fileName) {
        AttachmentInfoExample query = new AttachmentInfoExample();
        query.createCriteria().andFileNameEqualTo(fileName);
        AttachmentInfo update = new AttachmentInfo();
        update.setCategory("delete");
        attachmentInfoMapper.updateByExampleSelective(update, query);
    }

    @Override
    public void updateFile(AttachmentInfo attachmentInfo) {
        AttachmentInfoExample query = new AttachmentInfoExample();
        query.createCriteria().andFileNameEqualTo(attachmentInfo.getFileName());
        AttachmentInfo update = new AttachmentInfo();

        //只更新fileDesc
        update.setFileDesc(attachmentInfo.getFileDesc());
        attachmentInfoMapper.updateByExampleSelective(update, query);
    }
}
