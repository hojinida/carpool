package com.project.carpool.user.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotEmpty(message = "이메일 입력값이 존재하지 않습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty(message = "비밀번호 입력값이 존재하지 않습니다.")
    private String password;

    @NotEmpty(message = "이름 입력값이 존재하지 않습니다.")
    private String name;

    @NotEmpty(message = "전화번호 입력값이 존재하지 않습니다.")
    private String phoneNumber;
}
