package com.dtcenter.wsa_cofas_ema_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class CrossServiceConfig {

    private static final String GATEWAY = "http://localhost:8080";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Around("execution(public * *.*(..)) && @target(org.springframework.web.bind.annotation.RestController)")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();

        CrossServiceReference annotation = pjp.getTarget().getClass().getAnnotation(CrossServiceReference.class);

        if (!(result instanceof ResponseEntity<?> entity) || annotation == null) return result;

        Object body = entity.getBody();

        if (body == null) return entity;

        Object modified;

        if (body instanceof Page<?> pageBody) {
            List<Map<String, Object>> modifiedList = pageBody.getContent().stream()
                    .map(item -> processing(item, annotation))
                    .collect(Collectors.toList());
            modified = new PageImpl<>(modifiedList, pageBody.getPageable(), pageBody.getTotalElements());

        } else if (body instanceof List<?> listBody) {
            modified = listBody.stream()
                    .map(item -> processing(item, annotation))
                    .collect(Collectors.toList());
        } else {
            modified = processing(body, annotation);
        }

        return ResponseEntity.status(entity.getStatusCode())
                .headers(entity.getHeaders())
                .body(modified);
    }

    private Map<String, Object> processing(Object item, CrossServiceReference annotation) {
        try {

            String jsonStr = objectMapper.writeValueAsString(item);
            Map<String, Object> mapItem = objectMapper.readValue(jsonStr, Map.class);

            String originalField = annotation.originalField();
            String targetField = annotation.targetField();
            String destination = annotation.destination();

            if (mapItem.containsKey(originalField) && mapItem.get(originalField) != null) {
                String dataJson = restTemplate.getForObject(GATEWAY + "/" + destination + "/" + mapItem.get(originalField), String.class);
                if (dataJson != null) {
                    Map<String, Object> data = objectMapper.readValue(dataJson, Map.class);
                    mapItem.remove(originalField);
                    mapItem.put(targetField, data);
                }
            }

            return mapItem;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
