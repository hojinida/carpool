package com.project.carpool.user.application.dto;

import com.project.carpool.auth.domain.RefreshToken;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateResponse {
    private String accessToken;
    private String refreshToken;

    @Builder
    public UserCreateResponse(String accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken.getKey();
    }
}
