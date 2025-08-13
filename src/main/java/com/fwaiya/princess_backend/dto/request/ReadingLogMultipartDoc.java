package com.fwaiya.princess_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@Schema(name = "ReadingLogMultipartDoc", description = "multipart/form-data RequestBody (문서 전용)")
public class ReadingLogMultipartDoc {

    @Schema(
            description = "독서록 JSON 바디",
            implementation = ReadingLogRequest.class // ← ReadingLogRequest 구조를 Swagger에 그대로 노출
    )
    private ReadingLogRequest readingLog;

    @Schema(description = "표지 이미지 파일(선택)", type = "string", format = "binary")
    private MultipartFile coverImage;
}
