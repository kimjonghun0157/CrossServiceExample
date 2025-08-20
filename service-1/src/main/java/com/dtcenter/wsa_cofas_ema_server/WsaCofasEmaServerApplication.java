package com.dtcenter.wsa_cofas_ema_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableJpaAuditing
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WsaCofasEmaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsaCofasEmaServerApplication.class, args);
	}

}
