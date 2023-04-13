package com.project.carpool.message.domain.repository;

import com.project.carpool.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
