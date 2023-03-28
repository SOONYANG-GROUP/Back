//package com.campuscrew.campuscrew.oauth;
//
//import com.campuscrew.campuscrew.auth.PrincipalDetails;
//import com.campuscrew.campuscrew.domain.user.Role;
//import com.campuscrew.campuscrew.domain.user.User;
//import com.campuscrew.campuscrew.oauth.userinfo.GoogleUserInfo;
//import com.campuscrew.campuscrew.oauth.userinfo.NaverUserInfo;
//import com.campuscrew.campuscrew.oauth.userinfo.UserInfo;
//import com.campuscrew.campuscrew.repository.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        System.out.println(registrationId);
//        System.out.println("userRequest = " + userRequest.getAccessToken().getTokenValue());
//
//        UserInfo userInfo = null;
//        if (registrationId.equals("google")) {
//            userInfo = new GoogleUserInfo(attributes, registrationId);
//        } else if(registrationId.equals("naver")) {
//            userInfo = new NaverUserInfo(attributes, registrationId);
//        }
//        // 분기 1. 자동 가입 및 로그인을 하게 하자
//        User byEmail = userRepository
//                .findByEmail(userInfo.getEmail())
//                .orElse(null);
//
//        if(byEmail == null) {
//           byEmail = User
//                    .builder()
//                    .email(userInfo.getEmail())
//                    .role(Role.USER)
//                    .username(userInfo.getProvider() + "_" + userInfo.getProviderId())
//                    .name(userInfo.getName())
//                    .password(bCryptPasswordEncoder.encode(userInfo.getProvider() + "_" + userInfo.getProviderId()))
//                    .socialType(userInfo.getProvider())
//                    .providerId(userInfo.getProviderId())
//                    .createDate(LocalDateTime.now())
//                    .build();
//            userRepository.save(byEmail);
//        }
//        return new PrincipalDetails(byEmail, attributes);
//    }
//}
