package com.project.carpool.room.application;

import com.project.carpool.room.application.dto.RoomResponse;
import com.project.carpool.room.domain.Room;
import com.project.carpool.room.domain.repository.RoomRepository;
import com.project.carpool.room.presentation.dto.RoomCreateRequest;
import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    //채팅방 불러오기
    public List<RoomResponse> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        return roomRepository.findAll().stream()
                .map(room -> new RoomResponse(room.getId(),room.getMessages() ,room.getRoomName(),room.getEndTime(), room.getNumberOfPeople()))
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
                .endTime(chatRoom.get().getEndTime())
                .numberOfPeople(chatRoom.get().getNumberOfPeople())
                .build();
    }

    //채팅방 생성
    @Transactional
    public void createRoom(RoomCreateRequest request) {
        roomRepository.save(Room.builder()
                .roomName(request.getRoomName())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .numberOfPeople(1)
                .build());
    }
}
