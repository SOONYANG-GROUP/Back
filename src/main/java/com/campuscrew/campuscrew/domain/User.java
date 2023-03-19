package com.campuscrew.campuscrew.domain;

import com.campuscrew.campuscrew.dto.UserJoin;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@ToString(of = {"email", "socialId"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private Integer age;

    private String password;

    private String email;

    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private String refreshToken;



    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password =passwordEncoder.encode(this.password);
    }

    public void authorizeUser() {
        this.role = Role.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
