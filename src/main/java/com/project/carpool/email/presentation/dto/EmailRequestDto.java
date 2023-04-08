package com.project.carpool.email.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailRequestDto {
    @NotEmpty(message = "이메일 입력값이 존재하지 않습니다.")
    @Pattern(regexp = "^[^@]+@mju\\.ac\\.kr$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
