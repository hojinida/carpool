package com.project.carpool.auth.presentation;

import com.project.carpool.auth.application.AuthService;
import com.project.carpool.auth.application.dto.UserLoginResponse;
import com.project.carpool.auth.presentation.dto.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    //로그인 화면
    @GetMapping("/login")
    public String login() {
        return "index.html";
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.status(200)
                .body(authService.login(request));
    }

    //토큰 재발급
    @PostMapping("/auth/reissue")
    public ResponseEntity<UserLoginResponse> reissue(HttpServletRequest request) {
        return ResponseEntity.status(200)
                .body(authService.reissue(request));
    }
}
