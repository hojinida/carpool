package com.project.carpool.auth.domain;

<<<<<<< HEAD
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
=======
import lombok.*;
>>>>>>> 6b4c42c2946f3c21dfeafce58b142dddd0e2be07
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("auth")
@NoArgsConstructor
<<<<<<< HEAD
@Getter
=======
@Getter @Setter
>>>>>>> 6b4c42c2946f3c21dfeafce58b142dddd0e2be07
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
