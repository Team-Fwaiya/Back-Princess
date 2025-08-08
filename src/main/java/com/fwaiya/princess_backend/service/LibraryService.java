package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.dto.response.LibraryResponseDto;
import com.fwaiya.princess_backend.domain.Library;
import com.fwaiya.princess_backend.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    public List<LibraryResponseDto> getNearbyLibraries(String location) {
        // 최대 5개 조회
        PageRequest pageRequest = PageRequest.of(0, 5);

        // 위치 정보 파싱 ("경기도 고양시 덕양구" -> ["경기도", "고양시"])
        String[] locationParts = parseLocation(location);
        String city = locationParts[0];
        String district = locationParts[1];

        List<Library> libraries = null;

        // 시와 구 모두 있는 경우
        if (city != null && district != null) {
            libraries = libraryRepository.findBySidoContainingAndSigunguContaining(
                    city, district, pageRequest);
        }

        // 해당 지역에 도서관이 없거나 구가 없으면 시도만으로 검색
        if ((libraries == null || libraries.isEmpty()) && city != null) {
            libraries = libraryRepository.findBySidoContaining(city, pageRequest);
        }

        return libraries != null ? libraries.stream()
                .map(LibraryResponseDto::from)
                .collect(Collectors.toList()) : List.of();
    }

    private String[] parseLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return new String[]{null, null};
        }

        String[] parts = location.trim().split("\\s+");
        String city = null;
        String district = null;

        if (parts.length >= 1) {
            city = parts[0]; // 첫 번째가 시도
        }
        if (parts.length >= 2) {
            district = parts[1]; // 두 번째가 시군구
        }

        return new String[]{city, district};
    }
}