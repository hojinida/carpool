package com.project.carpool.message.presentation;

import com.project.carpool.message.domain.Message;
import com.project.carpool.message.domain.application.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/rooms/{roomId}/chat")
    @SendTo("/topic/rooms/{roomId}/chat")
    public Message sendMessage(@DestinationVariable Long roomId, Message message) {
        return messageService.sendMessage(roomId, message);
    }
}
