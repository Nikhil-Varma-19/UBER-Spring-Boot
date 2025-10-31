package com.example.uber.uber_backend.utilis;

import com.example.uber.uber_backend.exceptions.EmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String[] toMail,String subject,String content,String[] ccMail){
            try {
                SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
                simpleMailMessage.setText(content);
                simpleMailMessage.setTo(toMail);
                simpleMailMessage.setSubject(subject);
                if(ccMail != null)simpleMailMessage.setCc(ccMail);
                javaMailSender.send(simpleMailMessage);
                log.info("Email is send successfully");
            } catch (MailException e) {
                log.info("Cannot send the Email"+e.getMessage());
                throw  new EmailException(e.getMessage());
            }
    }
}
