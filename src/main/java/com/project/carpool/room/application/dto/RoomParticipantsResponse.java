package com.project.carpool.room.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomParticipantsResponse {
    List<String> participants;
    @Builder
    public RoomParticipantsResponse(List<String> participants) {
        this.participants = participants;
    }
}
