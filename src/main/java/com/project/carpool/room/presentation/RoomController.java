package com.project.carpool.room.presentation;

import com.project.carpool.room.application.RoomService;
import com.project.carpool.room.application.dto.RoomResponse;
import com.project.carpool.room.presentation.dto.RoomCreateRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String createRoom(@RequestBody RoomCreateRequest request, Model model) {
        log.info("채팅방 생성");
        roomService.createRoom(request);
        model.addAttribute("success", true);
        return "redirect:/chat/rooms";
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        log.info("채팅방 입장");
        RoomResponse room = roomService.findById(roomId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomName", room.getRoomName());
        model.addAttribute("messages", room.getMessages());
        return "chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    public String roomInfo(Model model, @PathVariable Long roomId) {
        RoomResponse room = roomService.findById(roomId);
        model.addAttribute("room", room);
        return "chat/roomInfo";
    }
}
