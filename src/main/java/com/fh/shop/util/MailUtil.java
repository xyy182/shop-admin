package com.fh.shop.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    public static  void sendMail(String subject,String content,String to){
        Properties prop = new Properties();
        prop.setProperty("mail.host", SystemConst.MAIL_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        Transport ts =null;
        try {
            //2、通过session得到transport对象
             ts=session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(SystemConst.MAIL_HOST, SystemConst.MAIL_FROM, SystemConst.MAIL_PASSWORD);
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(SystemConst.MAIL_FROM));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //邮件的标题
            message.setSubject(subject);
            //邮件的文本内容
            message.setContent(content, "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            if(null!=ts){
                try {
                    ts.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}


