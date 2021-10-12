package com.example.demo.service;

import com.example.demo.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class SendMailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;




    @Override
    public void mailSendForLinkVerification(String to, String text, Map<String, Object> map) {



    }

    @Override
    public void mailSend(String to, String subject, String text) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("shweta.singh@oodles.io");
        mail.setTo(to);
        mail.setText(text);
        mail.setSubject(subject);
        mailSender.send(mail);

    }

    }


