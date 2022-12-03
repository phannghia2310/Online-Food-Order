package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@PropertySource(value = "classpath:content.properties", encoding = "UTF-8")
@Service
public class MailServiceImp implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImp.class);
    private final JavaMailSender mailSender;

    @Value("${contentTitleMail}")
    String contentTitleMail;
    @Value("${email}")
    String emailFrom;

    @Autowired
    public MailServiceImp(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendEmail(String email, String content) {
        final boolean[] isSendMail = {false};
        new Thread(() -> {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
                messageHelper.setFrom(emailFrom);
                messageHelper.setTo(email);
                messageHelper.setSubject(contentTitleMail);
                messageHelper.setText(content, true);

                mailSender.send(mimeMessage);
                isSendMail[0] = true;
            } catch (MessagingException e) {
                log.error("ERROR SEND MAIL METHOD sendEnmail : ", e);
                isSendMail[0] = false;
            }
        }).start();
        return isSendMail[0];
    }
}
