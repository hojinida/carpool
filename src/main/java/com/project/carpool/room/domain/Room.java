package com.project.carpool.room.domain;

import com.project.carpool.message.domain.Message;
import com.project.carpool.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@RequiredArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "room",fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();
    private String roomName;
    private Long ownerId;
    private String startLocation;
    private String endLocation;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer numberOfPeople;

    public void increaseNumberOfPeople(){
        this.numberOfPeople++;
    }
    public void decreaseNumberOfPeople(){
        this.numberOfPeople--;
    }

    @Builder
    public Room(String roomName,Long ownerId ,String startLocation, String endLocation, LocalTime startTime, LocalTime endTime, Integer numberOfPeople) {
        this.roomName = roomName;
        this.ownerId = ownerId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfPeople = numberOfPeople;
    }
}
