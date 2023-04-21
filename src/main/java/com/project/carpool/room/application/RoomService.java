package com.project.carpool.room.application;

import com.project.carpool.auth.support.SecurityUtil;
import com.project.carpool.room.application.dto.RoomParticipantsResponse;
import com.project.carpool.room.application.dto.RoomResponse;
import com.project.carpool.room.domain.Room;
import com.project.carpool.room.domain.repository.RoomRepository;
import com.project.carpool.room.presentation.dto.RoomCreateRequest;
import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.user.domain.User;
import com.project.carpool.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    //채팅방 입장
    @Transactional
    public RoomResponse enterRoom(Long id,Long userId) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateRoom(room.get());
        if(!room.get().getUsers().contains(user)) {
            room.get().increaseNumberOfPeople();
        }
        return createRoomResponse(room.get());
    }

    //채팅방 퇴장
    @Transactional
    public void exitRoom(Long roomId){
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        User user= userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateRoom(null);
        room.get().decreaseNumberOfPeople();
    }

    //채팅방 생성
    @Transactional
    public Room createRoom(RoomCreateRequest request) {
        User user= userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return roomRepository.save(Room.builder()
                .roomName(request.getRoomName())
                .ownerId(user.getId())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .numberOfPeople(0)
                .build());
    }


    //채팅방 조회
    public RoomResponse findById(Long id){
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        return createRoomResponse(room.get());
    }

    //채팅방 삭제
    @Transactional
    public void deleteRoom(Long id){
        User user= userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateRoom(null);
        roomRepository.deleteById(id);
    }

    //참여인원 조회
    public RoomParticipantsResponse getParticipants(Long id){
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        return RoomParticipantsResponse.builder()
                .participants(room.get().getUsers().stream()
                        .map(User::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    //방장 조회
    public Long getOwnerId(Long roomId){
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        return room.get().getOwnerId();
    }

    private RoomResponse createRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .endTime(room.getEndTime())
                .numberOfPeople(room.getNumberOfPeople())
                .build();
    }
}
