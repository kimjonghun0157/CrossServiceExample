package com.dtcenter.wsa_cofas_ema_server.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrossServiceReference {

    String originalField();

    String targetField();

    String destination();

}
