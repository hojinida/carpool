package com.project.carpool.email.domain.repository;

import com.project.carpool.email.domain.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken,Long> {
    Optional<EmailToken> findByToken(String emailToken);
    void deleteAllByExpirationDateBefore(LocalDateTime now);
}
