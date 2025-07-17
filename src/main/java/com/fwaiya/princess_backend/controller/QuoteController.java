package com.fwaiya.princess_backend.controller;


import com.fwaiya.princess_backend.dto.response.QuoteResponse;
import com.fwaiya.princess_backend.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quotes")
@Tag(name = "08. 명언 관리", description = "랜덤으로 명언을 띄웁니다.")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @GetMapping
    public ResponseEntity<QuoteResponse> getQuote(){
        QuoteResponse quote = quoteService.getQuote();
        return ResponseEntity.ok(quote);
    }
}
