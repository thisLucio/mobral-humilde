package com.solinftec.equipment.equipmentchat.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solinftec.equipment.equipmentchat.config.KafkaConstants;
import com.solinftec.equipment.equipmentchat.dtos.EquipmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class EquipmentListener {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(
            groupId = KafkaConstants.EQUIPMENT_GROUP,
            topics = KafkaConstants.EQUIPMENT_TOPIC
    )
    public void listen(@Payload String equipment) throws JsonProcessingException {
        EquipmentDto equipmentDto = objectMapper.readValue(equipment, EquipmentDto.class);
        template.convertAndSend("/topic/equipment", equipmentDto);
    }
}
