package com.dtcenter.wsa_cofas_ema_server.rest.project;

import com.dtcenter.wsa_cofas_ema_server.config.CrossServiceReference;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossServiceReference(originalField = "user.username", targetField = "user", destination = "/api/users")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectSingleResponseDto> getProjectByCode(@PathVariable(value = "id") long id) {
        ProjectSingleResponseDto result = projectService.getProjectByCode(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ProjectSingleResponseDto> createProject(@Valid @RequestBody ProjectSingleRequestDto requestDto) {
        ProjectSingleResponseDto result = projectService.createProject(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}