package com.dtcenter.wsa_cofas_ema_server.rest.user;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserSingleResponseDto getUserById(Long id) {

        Optional<User> byId = userRepository.findById(id);

        User userNotFound = byId.orElseThrow(() -> new RuntimeException("User not found"));

        return UserSingleResponseDto.builder().username(userNotFound.getUsername()).build();
    }
}
