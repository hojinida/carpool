package com.project.carpool.email.presentation;

import com.project.carpool.email.application.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody @Validated UserEmailRequest request){
        emailService.createEmailToken(request.getEmail());
        return ResponseEntity.status(200).body("이메일 발송 성공");
    }
    
    @PostMapping("/auth")
    public ResponseEntity<String> authEmail(@RequestBody @Validated EmailTokenRequest request) {
        emailService.verifyEmail(request);
        return ResponseEntity.status(200).body("이메일 인증 성공");
    }
}
