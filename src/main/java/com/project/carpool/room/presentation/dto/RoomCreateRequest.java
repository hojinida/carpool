package com.project.carpool.room.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class RoomCreateRequest {
    private String roomName;
    private String startLocation;
    private String endLocation;
    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    public RoomCreateRequest(String roomName, String startLocation, String endLocation, LocalTime startTime, LocalTime endTime) {
        this.roomName = roomName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
