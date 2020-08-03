package com.danbai.ys.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @author danbai
 * @date 2019-10-29 16:26
 */
@Component
public class EmailUtil {
    @Value("${dbys.mail-sender}")
    private String sender;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发信模版
     * @param receiver
     * @param subject
     * @param emailTemplate
     * @param dataMap
     * @throws Exception
     */
    public void sendTemplateMail(String receiver, String subject, String emailTemplate, Map<String, Object> dataMap) throws Exception {
        Context context = new Context();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        String templateContent = templateEngine.process(emailTemplate, context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.setText(templateContent, true);
        javaMailSender.send(message);
    }
}
