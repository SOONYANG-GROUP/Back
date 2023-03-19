package com.campuscrew.campuscrew.oauth.userinfo;

public interface UserInfo {
    String getName();
    String getEmail();
    String getProviderId();
    String getProvider();
}
