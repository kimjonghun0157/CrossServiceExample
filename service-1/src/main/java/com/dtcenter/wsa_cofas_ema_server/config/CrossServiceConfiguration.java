package com.dtcenter.wsa_cofas_ema_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class CrossServiceConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}