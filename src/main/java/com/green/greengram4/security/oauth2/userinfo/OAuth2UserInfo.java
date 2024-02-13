package com.green.greengram4.security.oauth2.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

// 페북 구글이 얘를 상속받도록 셋팅
@AllArgsConstructor
@Getter
public abstract class OAuth2UserInfo {
    protected Map<String , Object> attributes;

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
