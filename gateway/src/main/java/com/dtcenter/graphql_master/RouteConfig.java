package com.dtcenter.graphql_master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    public static final RouteModel[] routes = {
            new RouteModel("module_project", "/api/projects/**", "localhost", "8081", true),
            new RouteModel("module_user", "/api/users/**", "localhost", "8082", true)
    };

    @Bean
    public RouteLocator doRoute(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder builtBuilder = builder.routes();

        for (RouteModel route : routes) {
            if (route.open) {
                builtBuilder.route(route.getId(), p -> p.path(route.getPath()).uri(route.getUri()));
            }
        }

        return builtBuilder.build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RouteModel {

        private String id;

        private String path;

        private String host;

        private String port;

        private boolean open;

        public String getUri() {
            return "http://" + host + ":" + port;
        }
    }
}