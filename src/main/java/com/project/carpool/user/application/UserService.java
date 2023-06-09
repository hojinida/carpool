package com.project.carpool.user.application;

import com.project.carpool.auth.domain.RefreshToken;
import com.project.carpool.auth.domain.repository.RefreshTokenRepository;
import com.project.carpool.auth.support.JwtTokenProvider;
import com.project.carpool.auth.support.SecurityUtil;
import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.user.application.dto.UserCreateResponse;
import com.project.carpool.user.domain.Status;
import com.project.carpool.user.domain.User;
import com.project.carpool.user.domain.repository.UserRepository;
import com.project.carpool.user.presentation.dto.StarUpdateRequest;
import com.project.carpool.user.presentation.dto.UserCreateRequest;
import com.project.carpool.user.presentation.dto.UserNameUpdateRequest;
import com.project.carpool.user.presentation.dto.UserPasswordUpdateRequest;
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

    //회원 탈퇴
    public void deleteUser(){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
    //회원 아이디
    public Long getCurrentUserId(){
        User user =userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return user.getId();
    }

    //회원 이름 변경
    public void updateName(UserNameUpdateRequest request){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateName(request.getName());
    }

    //회원 비밀번호 변경
    public void updatePassword(UserPasswordUpdateRequest request){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    //회원 비밀번호 확인
    public boolean checkPassword(UserPasswordUpdateRequest request){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
    public void updateStar(StarUpdateRequest request){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.getStar().updateStar(request.getPoint());
    }

    //별점 조회
    public Long getStar(){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return user.getStar().getPoint();
    }

    public void ready(){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if(user.getStatus()== Status.READY && user.getStatus() != Status.COMPLETE){
            user.statusWait();
        }else{
            user.statusReady();
        }
    }

    public void complete(){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.statusComplete();
    }
}
