package com.team42.churninsight.decision.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
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
    @Async
    public void handleRecommendation(RecommendationEvent event) {

        if(event.action() == null) {
            return;
        }
        EmailContent content = templateProvider.getTemplate(event.action());

        if(content == null) {
            System.out.println("No hay un template de email para esta opción.");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(customerMail);
        message.setSubject(content.subject());
        message.setText(content.body());

        mailSender.send(message);
    }
}
