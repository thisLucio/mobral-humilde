package com.solinftec.equipment.equipmentchat.controllers;

import com.solinftec.equipment.equipmentchat.config.KafkaConstants;
import com.solinftec.equipment.equipmentchat.dtos.MessageDto;
import com.solinftec.equipment.equipmentchat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private KafkaTemplate<String, MessageDto> template;
    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageDto message) {
        messageService.save(message);
        template.send(KafkaConstants.MESSAGE_TOPIC, message);
    }
}
