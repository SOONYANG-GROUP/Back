package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.dto.ProfileDto;

public interface UserRepositoryCustom {
    ProfileDto fetchProfile(Long id);
}
