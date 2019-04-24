package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.mailing.SendMailException;
import com.akveo.bundlejava.user.User;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordEmailService {
//    @Value("${sendgrid.noreply}")
//    private String from;
//
//    @Value("${sendgrid.reset-password.template-id}")
//    private String templateId;

//    @Autowired
//    private Mailer mailer;

    void send(User user, String resetPasswordUrl) throws SendMailException {
//        Mail mail = buildMail(user, resetPasswordUrl);
//        mailer.send(mail);
    }

//    private Mail buildMail(User user, String resetPasswordUrl) {
//        return new MailBuilder()
//                .from(from)
//                .to(user.getEmail())
//                .addTemplateData("resetPasswordUrl", resetPasswordUrl)
//                .templateId(templateId)
//                .build();
//    }
}
