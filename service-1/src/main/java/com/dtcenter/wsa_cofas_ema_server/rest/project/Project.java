package com.dtcenter.wsa_cofas_ema_server.rest.project;

import com.dtcenter.wsa_cofas_ema_server.rest.lib.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Project extends BaseEntity {

    private String code;

    private String name;

    private Long userId;

}
