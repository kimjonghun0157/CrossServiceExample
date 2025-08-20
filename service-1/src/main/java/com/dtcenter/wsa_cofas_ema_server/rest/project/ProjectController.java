package com.dtcenter.wsa_cofas_ema_server.rest.project;

import com.dtcenter.wsa_cofas_ema_server.config.CrossServiceReference;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossServiceReference(originalField = "userId", targetField = "user", destination = "/api/users")
public class ProjectController  {

}
