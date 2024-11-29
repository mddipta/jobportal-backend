package com.lawencon.jobportal.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @Async
    public void sendOtpEmail(String code, String target) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setSubject("Kode Verifikasi");
            message.setFrom(sender);
            message.setRecipients(MimeMessage.RecipientType.TO, target);

            String htmlContent = new String(Files
                    .readAllBytes(Paths.get("src/main/resources/templates/verification.html")));
            htmlContent = htmlContent.replace("{code}", code);

            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something Wrong");
        }
    }
}
