package com.akveo.bundlejava.mailing;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class Mailer {

    @Value("${sendgrid.key}")
    private String key;

    private SendGrid sendgrid;

    @PostConstruct
    private void init() {
        sendgrid = new SendGrid(key);
    }

    public void send(Mail mail) throws SendMailException {
        try {
            Request request = createRequest(mail);
            Response response = sendgrid.api(request);

            if (response.getStatusCode() != HttpStatus.ACCEPTED.value()) {
                throw new SendMailException("Incorrect http status: " + response.getStatusCode());
            }
        } catch (IOException e) {
            throw new SendMailException(e);
        }
    }

    private Request createRequest(Mail mail) throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return request;
    }
}
