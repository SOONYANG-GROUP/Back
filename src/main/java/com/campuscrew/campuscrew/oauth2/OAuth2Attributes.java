package com.campuscrew.campuscrew.oauth2;

import com.campuscrew.campuscrew.domain.user.Role;
import com.campuscrew.campuscrew.domain.user.SocialType;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.oauth2.userinfo.GoogleOAuth2UserInfo;
import com.campuscrew.campuscrew.oauth2.userinfo.NaverOAuth2UserInfo;
import com.campuscrew.campuscrew.oauth2.userinfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
// oauth2 로그인
@Getter
public class OAuth2Attributes {
    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuth2Attributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuth2Attributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
        if(socialType == SocialType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);

    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println(userNameAttributeName);
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }
    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
     * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl을 가져와서 build
     * email에는 UUID로 중복 없는 랜덤 값 생성
     * role은 GUEST로 설정
     */
    public User toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        System.out.println(oauth2UserInfo.getId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UUID.randomUUID());
        stringBuilder.append("@");
        stringBuilder.append(socialType.name().toLowerCase());
        stringBuilder.append(".com");
        String email = stringBuilder.toString();

        return User.builder()
                .socialType(socialType)
                .socialId(oauth2UserInfo.getId())
                .name(oauth2UserInfo.getName())
                .acceptAlarm(false)
                .createDate(LocalDateTime.now())
                .email(email)
                .nickname(oauth2UserInfo.getNickname())
                .role(Role.USER)
                .build();
    }
}
