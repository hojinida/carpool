package com.project.carpool.message.presentation;

import com.project.carpool.message.domain.Message;
import com.project.carpool.message.domain.application.MessageService;
import com.project.carpool.room.application.RoomService;
import com.project.carpool.room.application.dto.RoomResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final RoomService roomService;

    @MessageMapping("/rooms/{roomId}/chat")
    @SendTo("/topic/rooms/{roomId}")
    public Message sendMessage(@DestinationVariable Long roomId, Message message, StompHeaderAccessor accessor) {
        return messageService.sendMessage(roomId, message, accessor);
    }
    @GetMapping("/room/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        RoomResponse room=roomService.findById(roomId);
        model.addAttribute("roomName", room.getRoomName());
        List<Message> messages = messageService.getPreviousMessages(roomId);
        model.addAttribute("messages", messages);
        return "chat/roomdetail.html";
    }
    @GetMapping("/rooms/{roomId}/messages")
    public List<Message> getPreviousMessages(@PathVariable Long roomId) {
        // 새로운 메시지부터 오래된 메시지 순으로 정렬하여 반환합니다.
        return messageService.getPreviousMessages(roomId);
    }
}
