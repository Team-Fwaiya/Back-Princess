package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.dto.response.QuoteResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
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
            throw new GeneralException(ErrorCode.QUOTE_FETCH_FAILED);
        }
    }

}
