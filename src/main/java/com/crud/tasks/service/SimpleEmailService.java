package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    private JavaMailSender javaMailSender;
    private MailCreatorService mailCreatorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    public SimpleEmailService(JavaMailSender javaMailSender, MailCreatorService mailCreatorService) {
        this.javaMailSender = javaMailSender;
        this.mailCreatorService = mailCreatorService;
    }

    public void send(Mail mail) {
        LOGGER.info("Preparation for sending message...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Message sent");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createSimpleMessage(Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        /*if (isNotBlank(mail.getToCc())) {
            mailMessage.setCc(mail.getToCc());
        }*/
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {

        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardMail(mail.getMessage()), true);
        };
    };

}
