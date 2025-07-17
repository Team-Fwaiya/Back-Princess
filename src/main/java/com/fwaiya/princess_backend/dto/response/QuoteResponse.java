package com.fwaiya.princess_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuoteResponse {
    private String message;
    private String author;
}
