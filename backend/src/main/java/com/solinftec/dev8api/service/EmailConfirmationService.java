package com.solinftec.dev8api.service;

import com.solinftec.dev8api.entity.ApiUser;
import com.solinftec.dev8api.entity.EmailConfirmation;
import com.solinftec.dev8api.kafka.EmailConfirmationStructure;
import com.solinftec.dev8api.kafka.KafkaService;
import com.solinftec.dev8api.repository.EmailConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmailConfirmationService {

    @Autowired
    private EmailConfirmationRepository emailConfirmationRepository;

    private ApiUserService apiUserService;

    @Autowired
    public void setApiUserService(@Lazy ApiUserService apiUserService) {
        this.apiUserService = apiUserService;
    }

    @Autowired
    private KafkaService kafkaService;

    public void generateToken(ApiUser apiUser, Integer timeToExpire) {
        EmailConfirmation emailConfirmation = emailConfirmationRepository.save(EmailConfirmation.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(timeToExpire))
                .confirmedAt(null)
                .apiUser(apiUser)
                .build());
        sendEmailConfirmation(emailConfirmation);
    }

    private void sendEmailConfirmation(EmailConfirmation emailConfirmation) {
        kafkaService.sendEmailConfirmationRequestBodyToKafka(
                new EmailConfirmationStructure(
                        emailConfirmation.getApiUser().getUsername(),
                        emailConfirmation.getApiUser().getEmail(),
                        emailConfirmation.getToken()
                )
        );
    }

    public void confirmEmail(String token) {
        EmailConfirmation emailConfirmation = emailConfirmationRepository.findByToken(token);
        if (emailConfirmation == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token doesn't exist");
        } else if (emailConfirmation.getExpiredAt().isBefore(LocalDateTime.now()) || emailConfirmation.getConfirmedAt() != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token has already expired");
        }
        emailConfirmationRepository.save(EmailConfirmation.builder()
                .id(emailConfirmation.getId())
                .token(emailConfirmation.getToken())
                .createdAt(emailConfirmation.getCreatedAt())
                .expiredAt(emailConfirmation.getExpiredAt())
                .confirmedAt(LocalDateTime.now())
                .apiUser(emailConfirmation.getApiUser())
                .build());
        apiUserService.enable(token);
    }

    public EmailConfirmation findByToken(String token) {
        return emailConfirmationRepository.findByToken(token);
    }

    public void deleteExpiredToken() {
        List<EmailConfirmation> emailConfirmations = emailConfirmationRepository.findExpired();
        for (EmailConfirmation emailConfirmation : emailConfirmations) {
            apiUserService.delete(emailConfirmation.getApiUser().getId());
        }
        emailConfirmationRepository.deleteAll(emailConfirmationRepository.findExpired());
    }
}
