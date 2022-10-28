package com.solinftec.dev8api;

import com.solinftec.dev8api.config.StorageProperties;
import com.solinftec.dev8api.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Dev8apiApplication {
    public static void main(String[] args) {
        SpringApplication.run(Dev8apiApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            StorageService storageService;
            storageService = new StorageService();
            storageService.init();
        };
    }
}
