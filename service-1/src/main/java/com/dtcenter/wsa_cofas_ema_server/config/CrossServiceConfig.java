package com.dtcenter.wsa_cofas_ema_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CrossServiceConfig {

    @Value("${cross-service.gateway:http://localhost:8080}")
    private String gateway;

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    @Around("execution(public org.springframework.http.ResponseEntity *(..)) && @target(org.springframework.web.bind.annotation.RestController)")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();

        CrossServiceReference annotation = getAnnotation(pjp);

        if (annotation == null || !isValidResponse(result)) {
            return result;
        }

        ResponseEntity<?> entity = (ResponseEntity<?>) result;
        Object transformedBody = transformBody(entity.getBody(), annotation);

        return ResponseEntity.status(entity.getStatusCode())
                .headers(entity.getHeaders())
                .body(transformedBody);
    }

    private CrossServiceReference getAnnotation(ProceedingJoinPoint pjp) {
        return pjp.getTarget().getClass().getAnnotation(CrossServiceReference.class);
    }

    private boolean isValidResponse(Object result) {
        return result instanceof ResponseEntity<?> entity && entity.getBody() != null;
    }

    private Object transformBody(Object body, CrossServiceReference annotation) {
        if (body instanceof Page<?> page) {
            return transformPage(page, annotation);
        }
        if (body instanceof List<?> list) {
            return transformList(list, annotation);
        }
        return transformSingleItem(body, annotation);
    }

    private Page<Map<String, Object>> transformPage(Page<?> page, CrossServiceReference annotation) {
        List<Map<String, Object>> transformedContent = page.getContent().stream()
                .map(item -> transformToMap(item, annotation))
                .toList();
        return new PageImpl<>(transformedContent, page.getPageable(), page.getTotalElements());
    }

    private List<Map<String, Object>> transformList(List<?> list, CrossServiceReference annotation) {
        return list.stream()
                .map(item -> transformToMap(item, annotation))
                .toList();
    }

    private Map<String, Object> transformSingleItem(Object item, CrossServiceReference annotation) {
        return transformToMap(item, annotation);
    }

    private Map<String, Object> transformToMap(Object item, CrossServiceReference annotation) {
        try {
            Map<String, Object> dataMap = objectMapper.convertValue(item, Map.class);
            return processFieldTransformation(dataMap, annotation);
        } catch (Exception e) {
            log.error("객체 변환 실패", e);
            return Map.of(); // 빈 맵 반환
        }
    }

    private Map<String, Object> processFieldTransformation(Map<String, Object> dataMap, CrossServiceReference annotation) {
        String originalField = annotation.originalField();

        if (originalField.contains(".")) {
            return processNestedField(dataMap, annotation);
        }
        return processTopLevelField(dataMap, annotation);
    }

    private Map<String, Object> processNestedField(Map<String, Object> dataMap, CrossServiceReference annotation) {
        String originalField = annotation.originalField();
        String[] fieldPath = originalField.split("\\.");

        Optional<String> fieldValue = extractNestedValue(dataMap, fieldPath);
        if (fieldValue.isPresent()) {
            fetchAndReplaceData(dataMap, fieldValue.get(), annotation);
        }

        return dataMap;
    }

    private Map<String, Object> processTopLevelField(Map<String, Object> dataMap, CrossServiceReference annotation) {
        String originalField = annotation.originalField();

        if (dataMap.containsKey(originalField) && dataMap.get(originalField) != null) {
            String fieldValue = String.valueOf(dataMap.get(originalField));
            fetchAndReplaceData(dataMap, fieldValue, annotation);
            dataMap.remove(originalField); // 원본 필드 제거
        }

        return dataMap;
    }

    private Optional<String> extractNestedValue(Map<String, Object> dataMap, String[] fieldPath) {
        Object current = dataMap;

        // 마지막 키를 제외하고 경로 탐색
        for (int i = 0; i < fieldPath.length - 1; i++) {
            if (current instanceof Map<?, ?> map) {
                current = map.get(fieldPath[i]);
            } else {
                return Optional.empty();
            }
        }

        // 마지막 키의 값 추출
        if (current instanceof Map<?, ?> map) {
            Object value = map.get(fieldPath[fieldPath.length - 1]);
            return value != null ? Optional.of(String.valueOf(value)) : Optional.empty();
        }

        return Optional.empty();
    }

    private void fetchAndReplaceData(Map<String, Object> dataMap, String fieldValue, CrossServiceReference annotation) {
        try {
            String url = buildApiUrl(annotation.destination(), fieldValue);

            String responseJson = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);

            if (responseJson != null) {
                Map<String, Object> externalData = objectMapper.readValue(responseJson, Map.class);
                dataMap.put(annotation.targetField(), externalData);
                log.debug("외부 데이터 조회 성공: {}", url);
            }
        } catch (Exception e) {
            log.warn("외부 API 호출 실패: {}", e.getMessage());
            // 실패해도 원본 데이터는 유지
        }
    }

    private String buildApiUrl(String destination, String value) {
        String cleanDestination = destination.startsWith("/") ? destination.substring(1) : destination;
        return String.format("%s/%s/%s", gateway, cleanDestination, value);
    }
}


