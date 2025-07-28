package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.Library;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponseDto {

    private String libraryName;
    private String sido;
    private String sigungu;
    private String address;

    public static LibraryResponseDto from(Library library) {
        return LibraryResponseDto.builder()
                .libraryName(library.getLibraryName())
                .sido(library.getSido())
                .sigungu(library.getSigungu())
                .address(library.getAddress())
                .build();
    }
}