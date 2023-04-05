package com.project.carpool.user.application;

import com.project.carpool.common.exception.CustomException;
import com.project.carpool.common.exception.ErrorCode;
import com.project.carpool.user.domain.User;
import com.project.carpool.user.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return user.get();
    }
}
