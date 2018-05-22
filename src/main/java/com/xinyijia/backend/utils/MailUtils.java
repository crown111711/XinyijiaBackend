package com.xinyijia.backend.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 14:15
 */

public class MailUtils {

    private static final Logger log = LoggerFactory.getLogger(MailUtils.class);
    public static boolean sendTextMail(final MailSenderInfo mailSenderInfo) {

        Authenticator authenticator = null;
        Properties pro = mailSenderInfo.getProperties();
        if (mailSenderInfo.isValidate()) {
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailSenderInfo.getUserName(), mailSenderInfo.getPassword());
                }
            };
        }
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailSenderInfo.getFromAddress());
            mailMessage.setFrom(from);
            Address[] tos = new Address[mailSenderInfo.getToAddress().length];
            for (int i = 0; i < tos.length; i++) {
                tos[i] = new InternetAddress(mailSenderInfo.getToAddress()[i]);
            }
            mailMessage.setRecipients(Message.RecipientType.TO, tos);
            mailMessage.setSubject(mailSenderInfo.getSubject());
            mailMessage.setSentDate(new Date());
            String mailContent = mailSenderInfo.getContent();
            mailMessage.setText(mailContent);
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException e) {
            log.error("send mail fail toAddress:{} ", Arrays.toString(mailSenderInfo.getToAddress()), e);
        }
        return false;
    }

    public static boolean sendHtmlMail(final MailSenderInfo mailSenderInfo) {
        Authenticator authenticator = null;
        Properties pro = mailSenderInfo.getProperties();

        if (mailSenderInfo.isValidate()) {
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailSenderInfo.getUserName(), mailSenderInfo.getPassword());
                }
            };
        }
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {

            Message mailMessage = new MimeMessage(sendMailSession);

            Address from = new InternetAddress(mailSenderInfo.getFromAddress());

            mailMessage.setFrom(from);
            Address[] tos = new Address[mailSenderInfo.getToAddress().length];

            for (int i = 0; i < tos.length; i++) {
                tos[i] = new InternetAddress(mailSenderInfo.getToAddress()[i]);
            }
            mailMessage.setRecipients(Message.RecipientType.TO, tos);
            mailMessage.setSubject(mailSenderInfo.getSubject());
            mailMessage.setSentDate(new Date());
            Multipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
            multipart.addBodyPart(bodyPart);
            mailMessage.setContent(multipart);
            Transport.send(mailMessage);
            return true;

        } catch (MessagingException e) {
            log.error("send mail fail toAddresss{}",e);
        }
        return false;
    }

}
