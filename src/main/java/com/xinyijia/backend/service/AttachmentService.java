package com.xinyijia.backend.service;

import com.xinyijia.backend.domain.AttachmentInfo;
import com.xinyijia.backend.param.AttachmentFile;
import com.xinyijia.backend.param.AttachmentInfluence;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/16 17:42
 */
@Service
public interface AttachmentService {
    void saveAttachment(AttachmentInfo attachment);

    AttachmentInfo getOneByName(String fileName);

    void catToRelative(AttachmentInfluence attachmentInfluence);

    List<AttachmentFile> getDownFile();
}
