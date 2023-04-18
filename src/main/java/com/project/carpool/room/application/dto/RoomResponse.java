package com.project.carpool.room.application.dto;

import com.project.carpool.message.domain.Message;
import com.project.carpool.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private List<Message> messages;
    private String roomName;
    private LocalTime endTime;
    private Integer numberOfPeople;

    @Builder
    public RoomResponse(Long id, List<Message> messages,String roomName, LocalTime endTime, Integer numberOfPeople) {
        this.id = id;
        this.messages = messages;
        this.roomName = roomName;
        this.endTime = endTime;
        this.numberOfPeople = numberOfPeople;
    }
}
