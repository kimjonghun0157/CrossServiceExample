package com.dtcenter.wsa_cofas_ema_server.rest.project;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

        private final ProjectRepository projectRepository;



        @Transactional(readOnly = true)
        public ProjectSingleResponseDto getProjectByCode(long id) {


            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("프로젝트를 찾을 수 없습니다"));

            return ProjectSingleResponseDto.builder()
                    .code(project.getCode())
                    .name(project.getName())
                    .user(ProjectSingleResponseDto.UserDto.builder().username(project.getUsername()).build())
                    .build();
        }

        @Transactional
        public ProjectSingleResponseDto createProject(ProjectSingleRequestDto requestDto) {

            Project project = Project.builder()
                    .code(requestDto.getCode())
                    .name(requestDto.getName())
                    .username(requestDto.getUsername())
                    .build();

            Project savedProject = projectRepository.save(project);

            return ProjectSingleResponseDto.builder()
                    .code(savedProject.getCode())
                    .name(savedProject.getName())
                    .build();
        }
}
