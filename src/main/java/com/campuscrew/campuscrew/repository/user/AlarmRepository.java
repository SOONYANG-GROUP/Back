package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.domain.user.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
