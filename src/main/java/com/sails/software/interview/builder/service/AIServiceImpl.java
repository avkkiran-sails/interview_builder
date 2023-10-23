package com.sails.software.interview.builder.service;

import com.sails.software.interview.builder.model.ai.chatgpt.RequestBody;
import com.sails.software.interview.builder.model.ai.chatgpt.Choice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIServiceImpl {
    @Value("${ai.chatgpt.model}")
    private String model;

    @Value("${ai.chatgpt.url}")
    private String url;

    @Value("${ai.chatgpt.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    public List<Choice> getResponse(String prompt) {
        RequestBody requestBody = new RequestBody(prompt);
        requestBody.setModel(model);
        HttpEntity<RequestBody> httpEntity = new HttpEntity<>(requestBody, getHeaders());
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {});
        return (List<Choice>) response.getBody().get("choices");
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
