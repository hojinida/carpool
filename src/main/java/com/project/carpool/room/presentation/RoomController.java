package com.project.carpool.room.presentation;

import com.project.carpool.room.application.RoomService;
import com.project.carpool.room.application.dto.RoomResponse;
import com.project.carpool.room.presentation.dto.RoomCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {
    private final RoomService roomService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    public String room(Model model) {
        List<RoomResponse> rooms = roomService.findAllRoom();
        model.addAttribute("rooms", rooms);
        return "/chat/roomList";
    }
    // 채팅방 생성
    @PostMapping("/room")
    public String createRoom(@ModelAttribute("roomCreateRequest") RoomCreateRequest request, Model model) {
        roomService.createRoom(request);
        model.addAttribute("success", true);
        return "redirect:/chat/rooms";
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        RoomResponse room = roomService.findById(roomId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomName", room.getRoomName());
        model.addAttribute("messages", room.getMessages());
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    public String roomInfo(Model model, @PathVariable Long roomId) {
        RoomResponse room = roomService.findById(roomId);
        model.addAttribute("room", room);
        return "/chat/roomInfo";
    }
}
