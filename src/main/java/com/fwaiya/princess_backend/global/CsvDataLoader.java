package com.fwaiya.princess_backend.global;

import com.fwaiya.princess_backend.domain.Library;
import com.fwaiya.princess_backend.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
@Slf4j
public class CsvDataLoader implements CommandLineRunner {

    private final LibraryRepository libraryRepository;

    @Override
    public void run(String... args) throws Exception {
        // 이미 데이터가 있으면 로딩하지 않음
        if (libraryRepository.count() > 0) {
            log.info("도서관 데이터가 이미 존재합니다.");
            return;
        }

        log.info("CSV에서 도서관 데이터 로딩 시작...");

        try {
            ClassPathResource resource = new ClassPathResource("data/전국도서관표준데이터.csv");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), "EUC-KR")
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
                if (fields.length >= 20) {
                    Library library = Library.builder()
                            .libraryName(clean(fields[0]))  // 도서관명
                            .sido(clean(fields[1]))         // 시도명
                            .sigungu(clean(fields[2]))      // 시군구명
                            .address(clean(fields[17]))     // 주소
                            .build();

                    libraryRepository.save(library);
                    count++;
                }
            }

            br.close();
            log.info("도서관 데이터 로딩 완료. 총 {}개", count);

        } catch (Exception e) {
            log.error("CSV 로딩 실패", e);
        }
    }

    private String clean(String field) {
        if (field == null || field.trim().isEmpty()) {
            return null;
        }
        return field.trim().replace("\"", "");
    }
}