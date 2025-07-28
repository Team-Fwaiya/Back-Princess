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

    public List<LibraryResponseDto> getNearbyLibraries(String city, String district) {
        // 최대 5개 조회
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<Library> libraries = libraryRepository.findBySidoContainingAndSigunguContaining(
                city, district, pageRequest);

        // 해당 지역에 도서관이 없으면 시도만으로 검색
        if (libraries.isEmpty()) {
            libraries = libraryRepository.findBySidoContaining(city, pageRequest);
        }

        return libraries.stream()
                .map(LibraryResponseDto::from)
                .collect(Collectors.toList());
    }
}