package com.team42.churninsight.decision.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression(
        "!'${spring.mail.username:}'.isEmpty() && " +
        "!'${spring.mail.password:}'.isEmpty() && " +
        "!'${spring.mail.customer:}'.isEmpty()"
)
public class EmailNotificationListener {

    private final JavaMailSender mailSender;
    private final EmailTemplateProvider templateProvider;

    @Value("${spring.mail.username}")
    private String mail;

    @Value("${spring.mail.customer}")
    private String customerMail;

    public EmailNotificationListener(JavaMailSender mailSender, EmailTemplateProvider templateProvider) {
        this.mailSender = mailSender;
        this.templateProvider = templateProvider;
    }

    @EventListener
    public void handleRecommendation(RecommendationEvent event) {

        if(event.actionCode() == null) {
            return;
        }

        EmailContent content = templateProvider.getTemplate(event.actionCode());

        if(content == null) {
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mail);
            message.setTo(customerMail);
            message.setSubject(content.subject());
            message.setText(content.body());

            mailSender.send(message);

            event.emailSent().set(true);
        } catch (Exception e) {
            System.err.println("El correo no ha sido enviado" + e.getMessage());
        }

    }
}
