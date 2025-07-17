package com.fwaiya.princess_backend.global.constant;


import lombok.Getter;

// DTO에서 한국어로 변환해서 처리
// 하층민/평민/상인/귀족/공주
@Getter
public enum ReadingLevel {
    lower("하층민"),
    commoner("평민"),
    merchant("상인"),
    aristocrat("귀족"),
    princess("공주");

    private final String koreanName;

    private ReadingLevel(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public ReadingLevel nextLevel() {
        return switch (this) {
            case lower -> commoner;
            case commoner -> merchant;
            case merchant -> aristocrat;
            case aristocrat -> princess;
            case princess -> princess; // 최종 레벨
        };
    }
}
