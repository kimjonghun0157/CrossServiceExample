package com.dtcenter.wsa_cofas_ema_server.rest.project;

import com.dtcenter.wsa_cofas_ema_server.rest.lib.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

    private String code;

    private String name;

    private String username;

}
