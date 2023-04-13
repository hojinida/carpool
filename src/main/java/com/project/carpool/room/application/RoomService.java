package com.project.carpool.room.application;

import com.project.carpool.room.application.dto.RoomResponse;
import com.project.carpool.room.domain.Room;
import com.project.carpool.room.domain.repository.RoomRepository;
import com.project.carpool.room.presentation.dto.RoomCreateRequest;
import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    //채팅방 불러오기
    public List<RoomResponse> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        return roomRepository.findAll().stream()
                .map(room -> new RoomResponse(room.getId(),room.getMessages() ,room.getRoomName(), room.getNumberOfPeople()))
        .toList();
    }

    //채팅방 하나 불러오기
    public RoomResponse findById(Long id) {
        Optional<Room> chatRoom = roomRepository.findById(id);
        if (chatRoom.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        return RoomResponse.builder()
                .id(chatRoom.get().getId())
                .roomName(chatRoom.get().getRoomName())
                .numberOfPeople(chatRoom.get().getNumberOfPeople())
                .build();
    }

    //채팅방 생성
    public void createRoom(RoomCreateRequest request) {
        roomRepository.save(Room.builder()
                .roomName(request.getRoomName())
                .numberOfPeople(1)
                .build());
    }
}
