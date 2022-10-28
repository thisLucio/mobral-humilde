package com.solinftec.dev8api.task;

import com.solinftec.dev8api.service.EmailConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class EmailConfirmationTask {

    private final EmailConfirmationService emailConfirmationService;

    @Scheduled(fixedDelay = 60000, initialDelay = 60000)
    public void removeEmailConfirmationExpired() {
        emailConfirmationService.deleteExpiredToken();
    }
}
