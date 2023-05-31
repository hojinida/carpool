package com.project.carpool.user.presentation;

import com.project.carpool.user.application.UserService;
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
    @GetMapping("/id")
    public ResponseEntity<Long> getCurrentUserId() {
        log.info("getCurrentUserId");
        return ResponseEntity.ok().body(userService.getCurrentUserId());
    }

    @PostMapping
    public ResponseEntity<Void> updateName(@RequestBody @Validated UserNameUpdateRequest request){
        userService.updateName(request);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<Boolean> checkPassword(@RequestBody @Validated UserPasswordUpdateRequest request){
        return ResponseEntity.ok().body(userService.checkPassword(request));
    }
    @PostMapping
    public ResponseEntity<Void> updatePassword(@RequestBody @Validated UserPasswordUpdateRequest request){
        userService.updatePassword(request);
        return ResponseEntity.ok().build();
    }
}
