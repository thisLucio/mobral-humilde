package com.solinftec.email.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solinftec.email.domain.EmailStructure;
import com.solinftec.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
public class EmailListener {
    @Autowired
    EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.user.topic}", groupId = "${spring.kafka.user.group}")
    public void listen(@Payload String emailStructure) throws ValidationException, JsonProcessingException {
        EmailStructure object = objectMapper.readValue(emailStructure, EmailStructure.class);
        emailService.sendEmail(object);
    }
}
