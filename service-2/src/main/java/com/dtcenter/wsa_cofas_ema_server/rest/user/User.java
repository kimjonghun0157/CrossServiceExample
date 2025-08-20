package com.dtcenter.wsa_cofas_ema_server.rest.user;

import com.dtcenter.wsa_cofas_ema_server.rest.lib.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class User extends BaseEntity {

    private String username;

    private String email;

    private String password;
}
