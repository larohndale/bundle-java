package com.akveo.bundlejava.mailing;

import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Personalization;

import java.util.HashMap;
import java.util.Map;

public class MailBuilder {

    private String from;
    private String to;
    private HashMap<String, String> templateData = new HashMap<String, String>();
    private String templateId;

    public MailBuilder from(String from) {
        this.from = from;
        return this;
    }

    public MailBuilder to(String to) {
        this.to = to;
        return this;
    }

    public MailBuilder addTemplateData(String key, String val) {
        this.templateData.put(key, val);
        return this;
    }

    public MailBuilder templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public Mail build() {
        Email from = new Email(this.from);
        Personalization personalization = createPersonalization();

        Mail mail = new Mail();

        mail.setTemplateId(templateId);
        mail.setFrom(from);
        mail.addPersonalization(personalization);

        return mail;
    }

    private Personalization createPersonalization() {
        Personalization personalization = new Personalization();
        Email to = new Email(this.to);
        personalization.addTo(to);

        for (Map.Entry<String, String> entry : templateData.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }

        return personalization;
    }
}
