package cn.doo.code.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class FkEmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送邮件
     *
     * @param toEmail
     * @throws MessagingException
     */
    public void sendVerifyEmail(String toEmail, String email, String code) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("库存收益管理系统-登录验证码");
        helper.setFrom("a421028879@163.com");
        helper.setTo(toEmail);
        helper.setSentDate(new Date());
        helper.setText(getPostEmailContext(email, code), true);
        javaMailSender.send(mimeMessage);
    }

    /**
     * 生成emailHtml
     *
     * @param email
     * @param code
     * @return
     */
    private static String getPostEmailContext(String email, String code) {
        HashMap<String, Object> root = new HashMap<>(2);
        root.put("verify", code);
        root.put("username", email);
        return getEmailHtml(root, "email.ftl");
    }


    /**
     * 生成动态模板
     *
     * @param map
     * @param templateName
     * @return
     */
    private static String getEmailHtml(Map map, String templateName) {
        String htmlText = "";
        try {
            //设置fk路径目录
            String ftl = "D:\\idea-workspaces\\TourismLease\\src\\main\\resources\\templates";
            //String ftl = "E:\\ProgramCode\\IDEA_Code\\LearnWorkingSet\\D20210526_Boot\\TourismLease\\src\\main\\resources\\templates";
            Configuration configuration = new Configuration();
            configuration.setDirectoryForTemplateLoading(new File(ftl));
            Template template = configuration.getTemplate(templateName, "UTF-8");
            //渲染模板为html
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlText;
    }

    /**
     * 获取Fk模板
     *
     * @param name
     * @return
     */
    private Template getTemplate(String name) {
        //通过freemarkerd Configuration读取相应的ftl
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        //设定去哪里读取相应的ftl模板文件，指定模板路径
        cfg.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "ftl");
        try {
            //在模板文件目录中找到名称为name的文件
            Template template = cfg.getTemplate(name);
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 输出到控制台
     *
     * @param name
     * @param root
     * @throws TemplateException
     * @throws IOException
     */
    private void print(String name, Map<String, Object> root) throws TemplateException, IOException {
        //通过Template可以将模板文件输出到相应的流
        Template template = this.getTemplate(name);
        template.process(root, new PrintWriter(System.out));
    }


}
