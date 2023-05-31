package com.project.carpool.user.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StarUpdateRequest {
    @NotNull(message = "별점 입력값이 존재하지 않습니다.")
    public Long point;
    @Builder
    public StarUpdateRequest(Long point) {
        this.point = point;
    }
}
