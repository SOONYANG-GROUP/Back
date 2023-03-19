package com.campuscrew.campuscrew.oauth2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getNickname();

    public abstract String getId();

    public abstract String getName();

    public abstract String getImageUrl();

}
