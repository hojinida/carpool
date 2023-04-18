package com.project.carpool.user.application;

import com.project.carpool.auth.domain.RefreshToken;
import com.project.carpool.auth.domain.repository.RefreshTokenRepository;
import com.project.carpool.auth.support.JwtTokenProvider;
import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.user.application.dto.UserCreateResponse;
import com.project.carpool.user.domain.User;
import com.project.carpool.user.domain.repository.UserRepository;
import com.project.carpool.user.presentation.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원 가입
    @Transactional
    public UserCreateResponse joinUser(UserCreateRequest request){
        validateDuplicateUser(request.getEmail());//중복 회원 검증
        User user = createUser(request);//회원 생성

        String accessToken = jwtTokenProvider.createAccessToken(user);
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user);

        refreshTokenRepository.save(refreshToken);
        userRepository.save(user);
        return UserCreateResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //중복 회원 검증
    private void validateDuplicateUser(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isPresent()){
            throw new CustomException(ErrorCode.USER_DUPLICATE_EMAIL);
        }
    }

    //회원 생성
    private User createUser(UserCreateRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
