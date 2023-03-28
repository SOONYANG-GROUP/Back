package com.campuscrew.campuscrew.service;

import com.campuscrew.campuscrew.domain.user.Role;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.EditForm;
import com.campuscrew.campuscrew.dto.ProfileDto;
import com.campuscrew.campuscrew.dto.UserJoin;
import com.campuscrew.campuscrew.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(UserJoin userJoin) throws Exception {
        if (userRepository.findByEmail(userJoin.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 회원 입니다.");
        }

        if (userRepository.findByNickname(userJoin.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userJoin.getEmail())
                .password(userJoin.getPassword())
                .nickname(userJoin.getNickname())
                .age(userJoin.getAge())
                .role(Role.USER)
                .createDate(LocalDateTime.now())
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);

        return user;
    }

    public ProfileDto getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return userRepository.fetchProfile(user.getId());
    }

    public void editUser(EditForm editForm, String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        user.editUser(editForm);

    }
}
