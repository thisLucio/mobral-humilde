package com.solinftec.equipment.equipmentchat.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solinftec.equipment.equipmentchat.config.KafkaConstants;
import com.solinftec.equipment.equipmentchat.dtos.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(
            groupId = KafkaConstants.MESSAGE_GROUP,
            topics = KafkaConstants.MESSAGE_TOPIC
    )
    public void listen(@Payload String message) throws JsonProcessingException {
        MessageDto messageDto = objectMapper.readValue(message, MessageDto.class);
        template.convertAndSendToUser(messageDto.getTo(), "/queue/chat/", messageDto);
    }
}
