package com.fwaiya.princess_backend.domain;


import com.fwaiya.princess_backend.global.constant.Genre;
import com.fwaiya.princess_backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * [Book]
 * 책 정보를 저장하는 엔티티 클래스
 * - title, author, publisher, genre, coverImageUrl, hashtags 등 메타데이터 포함
 * - 독서록(ReadingLog)와 1:1로 연결되어 사용됨
 * - 생성일(createdAt), 수정일(updatedAt)은 BaseEntity에서 상속
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자 보호
@AllArgsConstructor // 모든 필드 포함한 생성자 자동 생성
@Builder // 객체 생성 시 빌더 패턴 사용 가능
@Table(name = "book") // DB 테이블명을 'book'으로 지정
public class Book extends BaseEntity {

    @Id // 기본 키(PK)
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE) // ID는 외부에서 직접 수정 불가
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(nullable = false, length = 200)
    private String title; // 책 제목

    @Column(nullable = false, length = 100)
    private String author; // 저자 이름


    @Enumerated(EnumType.STRING) // Enum을 문자열로 DB에 저장
    @Column(nullable = false, length = 50)
    private Genre genre; // 장르 (자기계발, 소설, 경제 등)



    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl; // S3에 저장된 표지 이미지 URL

    @Column(length = 200)
    private String hashtags; // 책과 관련된 해시태그 (예: #힐링 #인생책)


// createdAt, updatedAt은 BaseEntity에서 상속받음
    // createdAt → 엔티티 최초 저장 시 자동 기록
    // updatedAt → 엔티티 수정 시 자동 갱신

}