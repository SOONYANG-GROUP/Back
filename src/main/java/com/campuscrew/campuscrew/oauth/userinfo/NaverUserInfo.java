package com.campuscrew.campuscrew.oauth.userinfo;

import java.util.Map;

public class NaverUserInfo implements UserInfo{
    private Map<String, Object> attributes;
    private String provider;
    public NaverUserInfo(Map<String, Object> attributes, String provider) {
        this.attributes = attributes;
        this.provider = provider;
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("id");
    }

    @Override
    public String getProvider() {
        return provider;
    }
}
