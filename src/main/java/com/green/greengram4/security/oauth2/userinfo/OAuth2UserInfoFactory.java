package com.green.greengram4.security.oauth2.userinfo;

import com.green.greengram4.security.oauth2.SocialProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
// factory가 나오면 객체 생성하겠다 생각하면 됩니다.
public class OAuth2UserInfoFactory {
    public OAuth2UserInfo getOAuth2UserInfo(SocialProviderType socialProviderType,
                                            Map<String, Object> attrs){
        return switch (socialProviderType){
            case KAKAO -> new KakaoOAuth2UserInfo(attrs);
            case NAVER -> new NaverOAuth2UserInfo(attrs);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
