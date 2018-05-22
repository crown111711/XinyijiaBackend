package com.xinyijia.backend.utils;
import lombok.Data;

import java.util.Properties;
/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 14:14
 */
@Data
public class MailSenderInfo {

    // 发送邮件的服务器的IP和端口
    private String mailServerHost = "smtp.qq.com";
    private String mailServerPort = "587";
    // 邮件发送者的地址
    private String fromAddress = "378097217@qq.com";
    // 邮件接收者的地址
    private String[] toAddress;
    // 登陆邮件发送服务器的用户名和密码
    private String userName = "378097217@qq.com";
    private String password = "ihwuelaoegbmbged";
            //"hbevgcwmbvkebhga";  旧
              //ihwuelaoegbmbged 新
    //Qwert51
    // 是否需要身份验证
    private boolean validate = true;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private String[] attachFileNames;

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }
}
