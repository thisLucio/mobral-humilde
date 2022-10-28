package com.solinftec.email.configuration;

import com.solinftec.email.domain.EmailConfirmationStructure;
import com.solinftec.email.domain.EmailStructure;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.group}")
    private String group;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, group);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return props;
    }

    @Bean
    public ConsumerFactory<String, EmailStructure> consumerFactory() {
        try (JsonDeserializer<EmailStructure> jsonDeserializer = new JsonDeserializer<>(EmailStructure.class)) {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                    new StringDeserializer(),
                    jsonDeserializer.ignoreTypeHeaders().trustedPackages("*"));
        }
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, EmailStructure>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailStructure> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, EmailConfirmationStructure> consumerFactoryEmailConfirmation() {
        try (JsonDeserializer<EmailConfirmationStructure> jsonDeserializer = new JsonDeserializer<>(EmailConfirmationStructure.class)) {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                    new StringDeserializer(),
                    jsonDeserializer.ignoreTypeHeaders().trustedPackages("*"));
        }
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, EmailConfirmationStructure>> kafkaListenerContainerFactoryEmailConfirmation() {
        ConcurrentKafkaListenerContainerFactory<String, EmailConfirmationStructure> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryEmailConfirmation());

        return factory;
    }

}
