package com.dtcenter.wsa_cofas_ema_server.rest.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSingleResponseDto {

    private String username;

    private String email;
}
