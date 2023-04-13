package com.project.carpool.message.domain;

import com.project.carpool.room.domain.Room;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    private String sender;

    private String content;

    @Builder
    public Message(Room room, String sender, String content) {
        this.room = room;
        this.sender = sender;
        this.content = content;
    }

    private static Message createMessage(Room room, String sender, String content){
        Message message = new Message();
        message.setRoom(room);
        message.setSender(sender);
        message.setContent(content);
        return message;
    }
}
