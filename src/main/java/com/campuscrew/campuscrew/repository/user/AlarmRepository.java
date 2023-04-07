package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.domain.user.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Query("select count(alarm) from Alarm alarm where alarm.user.id = :id")
    Long countAlarmByUserId(@Param("id") Long id);
}
