package com.project.carpool.email.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TokenRequestDto {
    @NotEmpty
    private String token;
}
