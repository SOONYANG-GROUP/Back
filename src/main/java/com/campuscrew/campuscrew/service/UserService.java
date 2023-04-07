package com.campuscrew.campuscrew.service;

import com.campuscrew.campuscrew.domain.user.Alarm;
import com.campuscrew.campuscrew.domain.user.AlarmStatus;
import com.campuscrew.campuscrew.domain.user.Role;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.EditForm;
import com.campuscrew.campuscrew.dto.ProfileDto;
import com.campuscrew.campuscrew.dto.UserJoin;
import com.campuscrew.campuscrew.dto.user.AlarmDto;
import com.campuscrew.campuscrew.repository.user.AlarmRepository;
import com.campuscrew.campuscrew.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(UserJoin userJoin) throws Exception {
        if (userRepository.findByEmail(userJoin.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 회원 입니다.");
        }


        User user = User.builder()
                .name(userJoin.getName())
                .detailField(userJoin.getDetailField())
                .acceptAlarm(true)
                .email(userJoin.getEmail())
                .password(userJoin.getPassword())
                .createDate(LocalDateTime.now())
                .role(Role.USER)
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

    public ProfileDto getProfile(Long id) {
        return userRepository.fetchProfile(id);
    }


    public boolean switchAlarm(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElse(null);
        return user.switchAlarm();
    }


    public void editUser(EditForm editForm, String email) {
        User user = userRepository
                .findByEmail(email)
                .orElse(null);
        user.editUser(editForm);
    }

    public Long getAlarmCount(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return alarmRepository
                .countAlarmByUserId(user.getId(), AlarmStatus.UNCONFIRMED)
                .orElse(0L);
    }

    public List<AlarmDto> getAlarmList(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return alarmRepository.fetchAlarmList(user.getId());
    }
}
