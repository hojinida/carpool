package com.project.carpool.message.domain.application;

import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.message.domain.Message;
import com.project.carpool.room.domain.Room;
import com.project.carpool.room.domain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final RoomRepository roomRepository;

    public Message sendMessage(Long roomId, Message message) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        room.getMessages().add(message);
        message.setRoom(room);
        return message;
    }
}
