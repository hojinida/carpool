package com.project.carpool.user.domain.repository;

import com.project.carpool.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //이메일로 회원 찾기
    Optional<User> findByEmail(String email);
}
