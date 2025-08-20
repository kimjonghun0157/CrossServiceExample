package com.dtcenter.wsa_cofas_ema_server.rest.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSingleResponseDto> getUserById(@PathVariable(value = "id") Long id) {
        UserSingleResponseDto result = userService.getUserById(id);
        return ResponseEntity.ok(result);
    }
}
