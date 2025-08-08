package com.fwaiya.princess_backend.domain;

import com.fwaiya.princess_backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookstores")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookstore extends BaseEntity {

    @Column(name = "poi_nm", nullable = false)
    private String poiNm; // 서점명

    @Column(name = "ctprvn_nm", nullable = false)
    private String ctprvnNm; // 시도명

    @Column(name = "signgu_nm", nullable = false)
    private String signguNm; // 시군구명

    @Column(name = "rdnmadr_nm")
    private String rdnmadrNm; // 도로명주소
}