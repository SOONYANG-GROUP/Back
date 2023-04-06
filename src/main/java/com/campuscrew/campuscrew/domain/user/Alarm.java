package com.campuscrew.campuscrew.domain.user;

import com.campuscrew.campuscrew.domain.board.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public static Alarm createAlarm(Project project, User user) {
        Alarm alarm = new Alarm();
        alarm.setAlarmStatus(AlarmStatus.UNCONFIRMED);
        alarm.setProject(project);
        alarm.setCreateDate(LocalDateTime.now());
        user.addAlarm(alarm);
        return alarm;
    }
}
