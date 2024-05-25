package com.example.backend.service.impl;

import com.example.backend.email.EmailReader;
import com.example.backend.model.dto.EmailDTO;
import com.example.backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailDTO emailDTO){
        var message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(emailDTO.to());
        message.setSubject(emailDTO.subject());
        message.setText(emailDTO.body());
        mailSender.send(message);
    }

    public void sendCreateAccountEmail(String email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@email.com");
            helper.setTo(email);
            helper.setSubject("Conta DevMotel criada.");

            // Leitura do arquivo HTML
            String htmlBody = EmailReader.readEmailTemplate("templates/email_created.html");

            helper.setText(htmlBody, true); // true indicates HTML content
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    /*public void sendCreateAccountEmail(String email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@email.com");
            helper.setTo(email);
            helper.setSubject("Conta DevMotel criada.");

            String htmlBody = "<h1>Sua conta no site da DevMotel foi criada com sucesso.</h1>"
                    + "<p>Faça o login para acessar sua conta.</p>";
            helper.setText(htmlBody, true); // true indicates HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
            e.printStackTrace();
        }
    }*/
}
