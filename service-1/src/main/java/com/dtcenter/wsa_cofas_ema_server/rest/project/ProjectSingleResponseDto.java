package com.dtcenter.wsa_cofas_ema_server.rest.project;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectSingleResponseDto {


    private String code;

    private String name;

    private UserDto user;

    @Getter
    @Setter
    @Builder
    public static class UserDto {

        private String username;

        private String email;
    }
}
