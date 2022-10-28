package com.solinftec.email.listener;

import com.solinftec.email.domain.EmailConfirmationStructure;
import com.solinftec.email.domain.EmailStructure;
import com.solinftec.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {
    @Autowired
    EmailService emailService;

    @KafkaListener(
            topics = "${spring.kafka.user.topic}",
            groupId = "${spring.kafka.group}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload EmailStructure emailStructure) {
        emailService.sendEmail(emailStructure);
    }

    @KafkaListener(topics = "${spring.kafka.email-confirmation.topic}",
            groupId = "${spring.kafka.group}",
            containerFactory = "kafkaListenerContainerFactoryEmailConfirmation")
    public void listenEmailConfirmationTopic(@Payload EmailConfirmationStructure emailConfirmationStructure) {
        emailService.sendEmailConfirmation(emailConfirmationStructure);
    }

}
