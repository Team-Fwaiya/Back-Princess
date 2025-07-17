package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.dto.response.QuoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "https://korean-advice-open-api.vercel.app/api/advice";

    public QuoteResponse getQuote(){
        try {
            ResponseEntity<QuoteResponse> response = restTemplate.getForEntity(URL, QuoteResponse.class);
            return response.getBody();
        } catch (Exception e) {
            return new QuoteResponse("명언을 불러오지 못했습니다.", "시스템");
        }
    }

}
