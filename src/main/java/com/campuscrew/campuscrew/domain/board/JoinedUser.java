package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Join;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "JOINUSERS")
public class JoinedUser {
    @Id
    @Column(name = "joined_user_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private JoinUserStatus status;


    public static JoinedUser createAsManager(User user) {
        JoinedUser joinedUser = getDefaultJoinedUser(user);
        joinedUser.setStatus(JoinUserStatus.MANAGER);
        return joinedUser;
    }

    public static JoinedUser createAsMember(User user) {
        JoinedUser joinedUser = getDefaultJoinedUser(user);
        joinedUser.setStatus(JoinUserStatus.MANAGER);
        return joinedUser;
    }

    private static JoinedUser getDefaultJoinedUser(User user) {
        JoinedUser joinedUser = new JoinedUser();
        joinedUser.setUser(user);
        return joinedUser;
    }
//
//    @OneToMany(mappedBy = "joinedUser")
//    private List<RequiredField> requiredFields;

}
