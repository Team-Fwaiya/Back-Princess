package com.fwaiya.princess_backend.domain;


import jakarta.persistence.*;
import lombok.*;
import com.fwaiya.princess_backend.domain.entity.BaseEntity; // createdAt, updatedAt 포함된 상위 클래스

@Entity // JPA가 이 클래스를 테이블과 매핑하도록 지정
@Table(name = "book") // 실제 DB에 매핑될 테이블 이름을 "book"으로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터 없는 생성자를 생성하되, 외부에서 호출 못하도록 보호
@AllArgsConstructor // 모든 필드 값을 매개변수로 받는 생성자 자동 생성
@Builder // 빌더 패턴을 사용할 수 있게 설정
public class Book extends BaseEntity {

    @Id // 기본 키(PK) 설정
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 방식 사용
    private Long id;

    @Column(nullable = false, length = 200) // null 허용하지 않으며, 최대 길이 200자로 제한
    private String title; // 책 제목

    @Column(nullable = false, length = 100) // null 허용하지 않으며, 최대 길이 100자
    private String author; // 저자 이름

    @Column(length = 50) // 최대 길이 50자, null 허용됨
    private String genre; // 책 장르 (예: 에세이, 소설, 자기계발 등)

    @Column(name = "cover_image_url", length = 200)
    private String coverImageUrl; // 책 표지 이미지 URL (S3 경로)

    @Column(length = 200) // 최대 200자, null 허용
    private String hashtags; // 책과 관련된 해시태그 문자열

    // createdAt, updatedAt은 BaseEntity에서 상속받음
    // createdAt → 엔티티 최초 저장 시 자동 기록
    // updatedAt → 엔티티 수정 시 자동 갱신
}
