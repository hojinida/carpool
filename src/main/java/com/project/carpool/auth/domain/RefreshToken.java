package com.project.carpool.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("auth")
@NoArgsConstructor
@Getter @Setter
public class RefreshToken{
    @Id
    @Indexed
    private String key;
    private String value;
    @TimeToLive
    private Long expiredTime;

    @Builder
    public RefreshToken(String key,String value,Long expiredTime) {
        this.key = key;
        this.value=value;
        this.expiredTime = expiredTime;
    }
}
