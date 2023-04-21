package com.project.carpool.room.presentation;

import com.project.carpool.room.application.RoomService;
import com.project.carpool.room.application.dto.RoomParticipantsResponse;
import com.project.carpool.room.application.dto.RoomResponse;
import com.project.carpool.room.domain.Room;
import com.project.carpool.room.presentation.dto.RoomCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {
    private final RoomService roomService;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    public String room(Model model) {
        List<RoomResponse> rooms = roomService.findAllRoom();
        model.addAttribute("rooms", rooms);
        return "chat/roomList";
    }
    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomCreateRequest request) {
        Room room=roomService.createRoom(request);
        return ResponseEntity.ok().body(
                RoomResponse.builder()
                        .id(room.getId())
                        .roomName(room.getRoomName())
                        .endTime(room.getEndTime())
                        .numberOfPeople(room.getNumberOfPeople())
                        .build()
        );
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId, @RequestParam Long userId) {
        RoomResponse room = roomService.enterRoom(roomId,userId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("userId", userId); // userId를 Model에 추가
        model.addAttribute("roomName", room.getRoomName());
        model.addAttribute("messages", room.getMessages());
        return "chat/roomdetail";
    }

    // 채팅방 나가기
    @GetMapping("/room/exit/{roomId}")
    public void exitRoom(@PathVariable Long roomId) {
        roomService.exitRoom(roomId);
    }

    //채팅방 삭제
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok().build();
    }

    //참여인원 조회
    @GetMapping("/rooms/{roomId}/participants")
    public ResponseEntity<RoomParticipantsResponse> getParticipants(@PathVariable Long roomId) {
        log.info("참여인원 조회");
        return ResponseEntity.ok().body(roomService.getParticipants(roomId));
    }

    //방장 확인
    @GetMapping("/rooms/{roomId}/owner")
    public ResponseEntity<Long> getOwner(@PathVariable Long roomId) {
        log.info("방장 확인");
        return ResponseEntity.ok().body(roomService.getOwnerId(roomId));
    }
}
