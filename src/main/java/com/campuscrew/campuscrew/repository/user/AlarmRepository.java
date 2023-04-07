package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.domain.user.Alarm;
import com.campuscrew.campuscrew.domain.user.AlarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryCustom{
    @Query("select count(alarm) from Alarm alarm where alarm.user.id = :id and alarm.alarmStatus = :status")
    Optional<Long> countAlarmByUserId(@Param("id") Long id, @Param("status")AlarmStatus status);
}
