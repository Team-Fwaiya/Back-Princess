package com.fwaiya.princess_backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    // 허용된 이미지 확장자
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    /**
     * 프로필 이미지 업로드
     */
    public String uploadProfileImage(MultipartFile file) {
        validateImageFile(file);
        return uploadFile(file, "profiles/");
    }

    /**
     * 책 표지 이미지 업로드
     */
    public String uploadBookCoverImage(MultipartFile file) {
        validateImageFile(file);
        return uploadFile(file, "books/");
    }

    /**
     * 파일 업로드 공통 메서드
     */
    private String uploadFile(MultipartFile file, String directory) {
        try {
            // 고유한 파일명 생성
            String fileName = directory + UUID.randomUUID() + "_" + file.getOriginalFilename();

            // 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // S3에 파일 업로드
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);

            // 업로드된 파일의 URL 반환
            return amazonS3.getUrl(bucketName, fileName).toString();

        } catch (IOException e) {
            log.error("파일 업로드 실패: {}", e.getMessage());
            throw GeneralException.of(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String fileUrl) {
        try {
            String fileName = extractFileNameFromUrl(fileUrl);
            amazonS3.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            log.error("파일 삭제 실패: {}", e.getMessage());
            throw GeneralException.of(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * URL에서 파일명 추출
     */
    private String extractFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

    /**
     * 이미지 파일 검증
     */
    private void validateImageFile(MultipartFile file) {
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            throw GeneralException.of(ErrorCode.INVALID_PROFILE_IMAGE);
        }

        // 파일 크기 확인
        if (file.getSize() > MAX_FILE_SIZE) {
            throw GeneralException.of(ErrorCode.INVALID_PROFILE_IMAGE);
        }

        // 파일 확장자 확인
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw GeneralException.of(ErrorCode.INVALID_PROFILE_IMAGE);
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw GeneralException.of(ErrorCode.INVALID_PROFILE_IMAGE);
        }
    }
}