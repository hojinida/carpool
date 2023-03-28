package com.project.carpool.user.presentation;

import com.project.carpool.user.presentation.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @PostMapping
    public ResponseEntity<Void> join(@RequestBody @Validated UserCreateRequest request) {
        return ResponseEntity.ok().build();
    }
}
