package cn.doo.code.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Component
public class EmailUtils {
    @Autowired
    private static JavaMailSender javaMailSender;

    /**
     * 普通邮件发送
     * @param toEmail
     * @param context
     */
    public static void sendSimpleMail(String toEmail,String subject,String context) {
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("tsmengban@163.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        message.setTo(toEmail);
        // 设置邮件抄送人，可以有多个抄送人
        message.setCc("tsmengban@163.com");
        // 设置隐秘抄送人，可以有多个
        message.setBcc("tsmengban@163.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText(context);
        // 发送邮件
        javaMailSender.send(message);
    }


    /**
     * html邮件发送 + 附件
     * @param toEmail
     * @param subject
     * @param context
     * @param file
     * @param filename
     * @throws MessagingException
     */
    public void sendHtmlFileMail(String toEmail,String subject,String context,File file,String filename) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        helper.setSubject(subject);
        helper.setText(context,true);
        helper.addAttachment(filename,file);
        helper.setTo(toEmail);
        helper.setFrom("tsmengban@163.com");
        javaMailSender.send(mimeMessage);
    }


    /**
     * 发送html中带图片的
     * @param toEmail
     * @param subject
     * @param context
     * @param file
     * @param filename
     * @throws MessagingException
     */
    public void sendHtmlImgMail(String toEmail,String subject,String context,File file,String filename) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(subject);
        helper.setFrom("tsmengban@163.com");
        helper.setTo(toEmail);
        //helper.setCc("37xxxxx37@qq.com");
        //helper.setBcc("14xxxxx098@qq.com");
        helper.setSentDate(new Date());
        // src='cid:p01' 占位符写法 ，第二个参数true表示这是一个html文本
        helper.setText(context,true);
        // 第一个参数指的是html中占位符的名字，第二个参数就是文件的位置
        helper.addInline(filename,file);
        javaMailSender.send(mimeMessage);
    }
}
