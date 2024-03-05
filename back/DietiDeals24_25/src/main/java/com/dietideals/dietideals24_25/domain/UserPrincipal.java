package com.dietideals.dietideals24_25.domain;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class UserPrincipal implements OAuth2User {

    private UserEntity userEntity;
    private Map<String, Object> attributes;

    public UserPrincipal(UserEntity userEntity, Map<String, Object> attributes){
        this.userEntity = userEntity;
        this.attributes = attributes;
    }

    public static UserPrincipal create(UserEntity userEntity, Map<String, Object> attributes) {
        return new UserPrincipal(userEntity, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
