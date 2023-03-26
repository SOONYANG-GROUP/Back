package com.campuscrew.campuscrew.repository;

import com.campuscrew.campuscrew.domain.user.SocialType;
import com.campuscrew.campuscrew.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findByNickname(String nickname);
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String id);
}
