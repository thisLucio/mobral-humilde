package com.solinftec.dev8api.kafka;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class KafkaService {
    @Autowired
    private KafkaTemplate<String, UserStructure> kafkaUserTemplate;

    @Autowired
    private KafkaTemplate<String, EquipmentStructure> kafkaEquipmentTemplate;

    public void sendUserRequestBodyToKafka(UserStructure userStructure){
        kafkaUserTemplate.send(KafkaTopics.userTopic, userStructure);
    }

    public void sendEquipmentRequestBodyToKafka(EquipmentStructure equipmentStructure){
        kafkaEquipmentTemplate.send(KafkaTopics.equipmentTopic, equipmentStructure.getOperationType().getDescription(), equipmentStructure);
    }
}
