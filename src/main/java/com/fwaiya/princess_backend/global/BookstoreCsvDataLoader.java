package com.fwaiya.princess_backend.global;

import com.fwaiya.princess_backend.domain.Bookstore;
import com.fwaiya.princess_backend.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2) // 도서관 데이터 로딩 후에 실행
public class BookstoreCsvDataLoader implements CommandLineRunner {

    private final BookstoreRepository bookstoreRepository;

    @Override
    public void run(String... args) throws Exception {
        // 이미 데이터가 있으면 로딩하지 않음
        if (bookstoreRepository.count() > 0) {
            log.info("서점 데이터가 이미 존재합니다.");
            return;
        }

        log.info("CSV에서 서점 데이터 로딩 시작...");

        try {
            ClassPathResource resource = new ClassPathResource("data/서점데이터.csv");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), "UTF-8")
            );

            String line;
            boolean isFirst = true;
            int count = 0;

            while ((line = br.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 19 && !clean(fields[4]).isEmpty() && !clean(fields[10]).isEmpty() && !clean(fields[11]).isEmpty()) {
                    Bookstore bookstore = Bookstore.builder()
                            .poiNm(clean(fields[4]))       // POI_NM (서점명)
                            .ctprvnNm(clean(fields[10]))   // CTPRVN_NM (시도명)
                            .signguNm(clean(fields[11]))   // SIGNGU_NM (시군구명)
                            .rdnmadrNm(clean(fields[18]))  // RDNMADR_NM (도로명주소)
                            .build();

                    bookstoreRepository.save(bookstore);
                    count++;
                }
            }

            br.close();
            log.info("서점 데이터 로딩 완료. 총 {}개", count);

        } catch (Exception e) {
            log.error("서점 CSV 로딩 실패", e);
        }
    }

    private String clean(String field) {
        if (field == null || field.trim().isEmpty() || "null".equalsIgnoreCase(field.trim())) {
            return "";
        }
        return field.trim().replace("\"", "");
    }
}