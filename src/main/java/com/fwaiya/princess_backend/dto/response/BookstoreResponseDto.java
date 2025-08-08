package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.Bookstore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookstoreResponseDto {

    private String poiNm;      // 서점명
    private String ctprvnNm;   // 시도명
    private String signguNm;   // 시군구명
    private String rdnmadrNm;  // 도로명주소

    public static BookstoreResponseDto from(Bookstore bookstore) {
        return BookstoreResponseDto.builder()
                .poiNm(bookstore.getPoiNm())
                .ctprvnNm(bookstore.getCtprvnNm())
                .signguNm(bookstore.getSignguNm())
                .rdnmadrNm(bookstore.getRdnmadrNm())
                .build();
    }
}