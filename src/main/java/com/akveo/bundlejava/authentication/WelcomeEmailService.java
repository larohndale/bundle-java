package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.mailing.MailBuilder;
import com.akveo.bundlejava.mailing.Mailer;
import com.akveo.bundlejava.mailing.SendMailException;
import com.akveo.bundlejava.user.User;
import com.sendgrid.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WelcomeEmailService {
    @Value("${sendgrid.tanya}")
    private String from;

    @Value("${sendgrid.welcome.template-id}")
    private String templateId;

    @Autowired
    private Mailer mailer;

    void send(User user) throws SendMailException {
        Mail mail = buildMail(user);
        mailer.send(mail);
    }

    private Mail buildMail(User user) {
        return new MailBuilder()
                .from(from)
                .to(user.getEmail())
                .templateId(templateId)
                .build();
    }
}
