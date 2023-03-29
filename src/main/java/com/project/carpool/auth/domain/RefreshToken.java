package com.project.carpool.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RequiredArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60)
public class RefreshToken {
    @Id
    @Indexed
    private String key;
    private Long value;
    @Builder
    public RefreshToken(final String key, final Long value) {
        this.key = key;
        this.value = value;
    }
}
