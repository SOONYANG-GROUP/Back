package com.campuscrew.campuscrew.auth;

import com.campuscrew.campuscrew.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


// 1. /login 요청
// 2. oauth 인지, 일반 요청인지에 따라서 Service 클래스 결정
// 3. 일반 요청: form으로 전달받은 username에 따라 결정
//@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new PrincipalDetails(user))
                .orElse(null);
    } // 여기서 반환된 객체가 Security Session에 들어간다.

}
