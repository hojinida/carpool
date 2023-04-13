package com.project.carpool.email.application;

import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.email.domain.EmailToken;
import com.project.carpool.email.domain.repository.EmailTokenRepository;
import com.project.carpool.email.presentation.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
    private final EmailTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;
    @Transactional
    public void verifyEmail(TokenRequestDto request) throws CustomException {
        emailTokenRepository.deleteAllByExpirationDateBefore(LocalDateTime.now());
        EmailToken emailToken = emailTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new CustomException(ErrorCode.MAIL_NOT_FOUND));

        emailTokenRepository.delete(emailToken);
    }

    @Transactional
    public void createEmailToken(String receiverEmail) {
        // 이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken();
        emailTokenRepository.save(emailToken);

        // 이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText(emailToken.getToken());
        emailSenderService.sendEmail(mailMessage);
    }
}
