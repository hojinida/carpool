package com.project.carpool.room.application.dto;

import com.project.carpool.message.domain.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private List<Message> messages;
    private String roomName;
    private Integer numberOfPeople;

    @Builder
    public RoomResponse(Long id, List<Message> messages, String roomName, Integer numberOfPeople) {
        this.id = id;
        this.messages = messages;
        this.roomName = roomName;
        this.numberOfPeople = numberOfPeople;
    }
}
