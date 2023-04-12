package com.project.carpool.chat.domain.repository;

import com.project.carpool.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{
}
