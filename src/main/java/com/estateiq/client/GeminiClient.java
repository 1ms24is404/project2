package com.estateiq.client;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class GeminiClient {

    private final RestTemplate restTemplate;
    private final String apiKey;

    public GeminiClient(RestTemplate restTemplate, @Value("${gemini.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public String generateContent(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] { Map.of("text", prompt) })
                }
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        Map response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class).getBody();

        if (response == null) {
            throw new RestClientException("Empty Gemini response");
        }

        return extractText(response);
    }

    private String extractText(Map response) {
        try {
            var candidates = (java.util.List<?>) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return "";
            }
            Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
            Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
            java.util.List<?> parts = (java.util.List<?>) content.get("parts");
            if (parts == null || parts.isEmpty()) {
                return "";
            }
            Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);
            Object text = firstPart.get("text");
            return text == null ? "" : text.toString();
        } catch (Exception ex) {
            throw new RestClientException("Unable to parse Gemini response", ex);
        }
    }
}
