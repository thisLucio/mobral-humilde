package com.solinftec.dev8api.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaUserTemplate;
    private final KafkaTemplate<String, Object> kafkaEquipmentTemplate;
    private final KafkaTemplate<String, Object> kafkaEmailConfirmationTemplate;

    public KafkaService(KafkaTemplate<String, Object> kafkaUserTemplate, KafkaTemplate<String, Object> kafkaEquipmentTemplate, KafkaTemplate<String, Object> kafkaEmailConfirmationTemplate) {
        this.kafkaUserTemplate = kafkaUserTemplate;
        this.kafkaEquipmentTemplate = kafkaEquipmentTemplate;
        this.kafkaEmailConfirmationTemplate = kafkaEmailConfirmationTemplate;
    }

    public void sendUserRequestBodyToKafka(UserStructure userStructure) {
        kafkaUserTemplate.send(KafkaTopics.USER_TOPIC, userStructure);
    }

    public void sendEquipmentRequestBodyToKafka(EquipmentStructure equipmentStructure) {
        kafkaEquipmentTemplate.send(KafkaTopics.EQUIPMENT_TOPIC, equipmentStructure.getOperationType().getDescription(), equipmentStructure);
    }

    public void sendEmailConfirmationRequestBodyToKafka(EmailConfirmationStructure emailConfirmationStructure) {
        kafkaEmailConfirmationTemplate.send(KafkaTopics.EMAIL_CONFIRMATION_TOPIC, emailConfirmationStructure);
    }

}
