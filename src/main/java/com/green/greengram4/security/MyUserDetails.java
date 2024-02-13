package com.green.greengram4.security;

import com.green.greengram4.user.model.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {
    //UserDetails 로컬 로그인 시 사용
    //OAuth2User 소셜 로그인 시 사용

    private MyPrincipal myPrincipal;
    private Map<String, Object> attributes;
    private UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(myPrincipal == null) {
            return null;
        }
        return this.myPrincipal.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        // ROLE_ADMIN이 들어가 ADMIN으로 가공 필요
    }

    // 1. 루틴이용 방법, return에 셋팅해야 합니다.
    // 2. 커스터마이징 방법
    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return userEntity.getUid(); }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
