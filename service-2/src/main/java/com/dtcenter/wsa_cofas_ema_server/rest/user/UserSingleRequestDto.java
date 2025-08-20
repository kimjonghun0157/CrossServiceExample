package com.dtcenter.wsa_cofas_ema_server.rest.user;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSingleRequestDto {

    @NotBlank(message = "사용자명은 필수입니다")
    private String username;

    @NotBlank(message = "이메일은 필수입니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
}