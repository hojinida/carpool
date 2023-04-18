package com.project.carpool.message.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.carpool.room.domain.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId")
    @JsonIgnore
    private Room room;
    private String sender;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public Message(Room room, String sender, String content, LocalDateTime createdAt) {
        this.room = room;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
    }


    private static Message createMessage(Room room, String sender, String content) {
        return Message.builder()
                .room(room)
                .sender(sender)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
