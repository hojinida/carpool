package com.project.carpool.room.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomCreateRequest {
    private String roomName;

    public RoomCreateRequest(String roomName) {
        this.roomName = roomName;
    }
}
