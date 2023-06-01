package com.project.carpool.user.presentation;

import com.project.carpool.user.application.UserService;
import com.project.carpool.user.presentation.dto.StarUpdateRequest;
import com.project.carpool.user.presentation.dto.UserCreateRequest;
import com.project.carpool.user.presentation.dto.UserNameUpdateRequest;
import com.project.carpool.user.presentation.dto.UserPasswordUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<Void> join(@RequestBody @Validated UserCreateRequest request) {
        userService.joinUser(request);
        return ResponseEntity.ok().build();
    }
    //회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(){
        userService.deleteUser();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/id")
    public ResponseEntity<Long> getCurrentUserId() {
        log.info("getCurrentUserId");
        return ResponseEntity.ok().body(userService.getCurrentUserId());
    }

    @PostMapping("/name")
    public ResponseEntity<Void> updateName(@RequestBody @Validated UserNameUpdateRequest request){
        userService.updateName(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/password/check")
    public ResponseEntity<Boolean> checkPassword(@RequestBody @Validated UserPasswordUpdateRequest request){
        return ResponseEntity.ok().body(userService.checkPassword(request));
    }
    @PostMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Validated UserPasswordUpdateRequest request){
        userService.updatePassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/star")
    public ResponseEntity<Void> updateStar(@RequestBody @Validated StarUpdateRequest request){
        userService.updateStar(request);
        return ResponseEntity.ok().build();
    }

    //별점 조회
    @GetMapping("/star")
    public ResponseEntity<Long> getStar(){
        return ResponseEntity.ok().body(userService.getStar());
    }

    @GetMapping("/ready")
    public ResponseEntity<Void> ready(){
        userService.ready();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/complete")
    public ResponseEntity<Void> complete(){
        userService.complete();
        return ResponseEntity.ok().build();
    }
}
