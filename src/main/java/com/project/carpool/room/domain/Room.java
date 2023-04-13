package com.project.carpool.room.domain;

import com.project.carpool.message.domain.Message;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@RequiredArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
    private String roomName;
    private Integer numberOfPeople;
    @Builder
    public Room(String roomName, Integer numberOfPeople) {
        this.roomName = roomName;
        this.numberOfPeople = numberOfPeople;
    }

}
