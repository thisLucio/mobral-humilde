package com.solinftec.equipment.equipmentchat.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class MessageProducerConfig {
    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(KafkaConstants.MESSAGE_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
