package com.solinftec.dev8api.repository;

import com.solinftec.dev8api.entity.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Long> {
    EmailConfirmation findByToken(String token);

    @Query("SELECT e FROM EmailConfirmation e WHERE e.confirmedAt = null and e.expiredAt < CURRENT_TIMESTAMP")
    List<EmailConfirmation> findExpired();
}
