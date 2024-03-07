//package com.dietideals.dietideals24_25.services.impl;
//
//import com.dietideals.dietideals24_25.domain.UserPrincipal;
//import com.dietideals.dietideals24_25.domain.entities.UserEntity;
//import com.dietideals.dietideals24_25.repositories.UserRepository;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class GoogleUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private UserRepository userRepository;
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
//
//        UUID id = oAuth2User.getAttribute("id");
//        String name = oAuth2User.getAttribute("name");
//        String email = oAuth2User.getAttribute("email");
//
//        UserEntity userEntity = userRepository.findById(id).orElseGet( () -> {
//            UserEntity newUser = new UserEntity();
//            newUser.setId(id);
//            newUser.setName(name);
//            newUser.setEmail(email);
//            return userRepository.save(newUser);
//        });
//        return UserPrincipal.create(userEntity, oAuth2User.getAttributes());
//    }
//}
