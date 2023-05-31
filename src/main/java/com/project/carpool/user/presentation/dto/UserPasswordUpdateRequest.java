package com.project.carpool.user.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordUpdateRequest {
    @NotEmpty(message = "비밀번호 입력값이 존재하지 않습니다.")
    private String password;
}
