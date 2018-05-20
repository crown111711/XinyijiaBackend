package com.xinyijia.backend.param;

import lombok.Data;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/19 20:52
 */
@Data
public class AttachmentFile {
    String fileName;
    String fileUrl;
    String fileDesc;
    String oldName;
    String fileType;
    String fileSize;
    String createTime;

}
