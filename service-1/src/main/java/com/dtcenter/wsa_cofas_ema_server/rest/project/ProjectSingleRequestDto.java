package com.dtcenter.wsa_cofas_ema_server.rest.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectSingleRequestDto {

    @NotBlank(message = "프로젝트 코드 필수입니다")
    private String code;

    @NotBlank(message = "프로젝트 이름은 필수입니다")
    private String name;

    @NotBlank(message = "담당자 이름은 필수입니다")
    private String username;
}
