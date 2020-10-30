package com.swustacm.poweroj.common.email;

import com.swustacm.poweroj.common.CodeEnum;
import com.swustacm.poweroj.common.CustomException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @description:  邮件发送配置
 * @author 张成云
 */
@Component
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String FROM_MAIL_SMTP ;
    @Value("${spring.mail.username}")
    private String FROM_MAIL_NAME ;

    @Value("${spring.mail.password}")
    private String FROM_MAIL_PASS ;

    private Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

    @Async
    public void sendMail(MailEnum mailEnum , Map<String,Object> model , String email){
        if (email == null){
            return;
        }
        String content;
        try{
            configuration.setClassForTemplateLoading(this.getClass(),"/email");
            Template template = configuration.getTemplate(mailEnum.getFtl());
            content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(CodeEnum.SEND_MAIL_ERROR);
        }

        try{
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            final Properties p = System.getProperties();
            //开启debug调试
            //p.setProperty("mail.debug","true");
            //设置邮件服务器主机名
            p.setProperty("mail.smtp.host",FROM_MAIL_SMTP);
            //设置服务器需要身份验证
            p.setProperty("mail.smtp.auth","true");
            p.setProperty("mail.smtp.user",FROM_MAIL_NAME);
            p.setProperty("mail.smtp.pass", FROM_MAIL_PASS);
            p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            p.setProperty("mail.smtp.socketFactory.fallback", "false");
            p.setProperty("mail.smtp.localhost", "mail.digu.com");
            /**
             * 邮箱发送服务器端口,这里设置为465端口
            */
            p.setProperty("mail.smtp.port", "465");
            p.setProperty("mail.smtp.socketFactory.port", "465");
            /**
             * 根据邮件会话属性和密码验证器构造一个发送邮件的session
             */
            Session session = Session.getInstance(p, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(p.getProperty("mail.smtp.user"), p.getProperty("mail.smtp.pass"));
                }
            });
            Message message = new MimeMessage(session);
            /**
             * 消息发送的主题
             */
            message.setSubject(mailEnum.getType());
            /**
             * 接受消息的人
             */
            message.setReplyTo(InternetAddress.parse(FROM_MAIL_NAME));
            /**
             * 消息的发送者
             */
            message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user")));

            //设置抄送防止报554错误
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(FROM_MAIL_NAME));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            message.setSentDate(new Date());

            Multipart mainPart = new MimeMultipart();
            /**
             * 创建一个包含HTML内容的MimeBodyPart
             */
            BodyPart html = new MimeBodyPart();
            /**
             * 设置HTML内容
             */
            html.setContent(content, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            /**
             * 将MiniMultipart对象设置为邮件内容
             */
            message.setContent(mainPart);
            message.saveChanges();
            Transport.send(message);
            System.out.println("发送成功——————————");

        }catch(Exception e){
            e.printStackTrace();
            throw new CustomException(CodeEnum.SEND_MAIL_ERROR);
        }

    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("date",1111111);
        map.put("code",111111);
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.sendMail(MailEnum.CODE_MAIL,map,"zcy_163com@163.com");
        System.out.println("发送邮件");
    }

}
