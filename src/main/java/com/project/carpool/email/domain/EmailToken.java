package com.project.carpool.email.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EmailToken {
    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;
    private static final int EMAIL_TOKEN_LENGTH=12;

    @Id
    private Long id;
    private String token;

    private LocalDateTime expirationDate;

    public static EmailToken createEmailToken() {
        return EmailToken.builder()
                .token(RandomStringUtils.randomAlphanumeric(EMAIL_TOKEN_LENGTH))
                .expirationDate(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE))
                .build();
    }

    @Builder
    public EmailToken(String token, LocalDateTime expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }
}
