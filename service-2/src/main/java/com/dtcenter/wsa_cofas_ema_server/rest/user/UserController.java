package com.dtcenter.wsa_cofas_ema_server.rest.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<UserSingleResponseDto> getUserByUsername(@PathVariable String username) {

        System.out.println("요청 들어옴");
        UserSingleResponseDto result = userService.getUserByUsername(username);
        return ResponseEntity.ok(result);
    }


    @PostMapping
    public ResponseEntity<UserSingleResponseDto> createUser(@Valid @RequestBody UserSingleRequestDto requestDto) {
        UserSingleResponseDto result = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
