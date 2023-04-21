package com.project.carpool.message.domain.application;

import com.project.carpool.auth.support.JwtTokenProvider;
import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.message.domain.Message;
import com.project.carpool.message.domain.repository.MessageRepository;
import com.project.carpool.room.domain.Room;
import com.project.carpool.room.domain.repository.RoomRepository;
import com.project.carpool.user.domain.User;
import com.project.carpool.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public Message sendMessage(Long roomId, Message message, StompHeaderAccessor accessor) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        String accessToken = accessor.getFirstNativeHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        String email=jwtTokenProvider.getUsername(accessToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        message.setRoom(room);
        message.setSender(user.getName());
        message.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/chat", message);
        return message;
    }

    public List<Message> getPreviousMessages(Long RoomId){
        return messageRepository.findAllByRoomIdOrderByCreatedAtDesc(RoomId);
    }
}
