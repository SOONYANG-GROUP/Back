package com.campuscrew.campuscrew.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    MANAGER("ROLE_ADMIN", "관리자"), USER("ROLE_USER", "손님"), GUEST("ROLE_GUEST", "일반 사용자");
    private final String name;
    private final String title;
}
