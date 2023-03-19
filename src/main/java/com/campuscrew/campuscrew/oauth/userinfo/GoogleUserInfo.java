package com.campuscrew.campuscrew.oauth.userinfo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GoogleUserInfo implements UserInfo{
    private String provider;
    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes, String provider) {
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
        return (String)attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return provider;
    }
}
