package com.dtcenter.wsa_cofas_ema_server.rest.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserSingleResponseDto getUserByUsername(String username) {
        User user = (User) userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        return UserSingleResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public UserSingleResponseDto createUser(UserSingleRequestDto requestDto) {

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다");
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return UserSingleResponseDto.builder()
                .username(savedUser.getUsername())
                .build();
    }
}
