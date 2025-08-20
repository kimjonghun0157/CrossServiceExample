package com.dtcenter.wsa_cofas_ema_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WsaCofasEmaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsaCofasEmaServerApplication.class, args);
	}

	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}
}
