package com.solinftec.email.services;

import com.solinftec.email.domain.EmailStructure;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EmailStructure emailStructure) {
        javaMailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setTo(emailStructure.getEmail());
                message.setSubject("Welcome, " + emailStructure.getUsername());
                message.setText("<h1>Welcome, " + emailStructure.getUsername() + ".</h1>" +
                        "<h2>Your registration was successful.</h2>" +
                        "<h2>Now you are a JEDI.</h2>" +
                        "<img src='cid:baby-yoda'>", true);
                message.addInline("baby-yoda", new ClassPathResource("baby-yoda.gif"));
            }
        });
    }
}
