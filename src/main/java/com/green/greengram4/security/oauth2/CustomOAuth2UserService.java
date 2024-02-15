package com.green.greengram4.security.oauth2;

import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.security.oauth2.userinfo.OAuth2UserInfo;
import com.green.greengram4.security.oauth2.userinfo.OAuth2UserInfoFactory;
import com.green.greengram4.user.UserMapper;
import com.green.greengram4.user.model.UserModel;
import com.green.greengram4.user.model.UserSelDto;
import com.green.greengram4.user.model.UserSignupProcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory factory;

    // 카카오 등 호출되면 이 친구가 호출이 되며 결과값이 userRequest로 들어옵니다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        // 오버라이딩으로 얘가 실행되지만 부모의 메소드를 호출합니다.
        // 무언가 정리해서 저장합니다.
        try{
            return this.process(userRequest, user);
        }catch (AuthenticationException e){
            throw e;
        }catch (Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    public OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user){
        SocialProviderType socialProviderType
                = SocialProviderType.valueOf(userRequest.getClientRegistration()
                                                        .getRegistrationId()
                                                        .toUpperCase()) ;
        // getRegistrationId은 야믈에서 kakao: 의 kakao입니다.

        OAuth2UserInfo oAuth2UserInfo = factory.getOAuth2UserInfo(socialProviderType, user.getAttributes());
        UserSelDto dto = UserSelDto.builder()
                .providerType(socialProviderType.name())
                .uid(oAuth2UserInfo.getId())
                .build();
        UserModel savedUser = mapper.selUser(dto);
        if(savedUser == null){ // 회원가입 처리
            savedUser = signupUser(oAuth2UserInfo, socialProviderType);
        }

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(savedUser.getIuser())
                .build();
        myPrincipal.getRoles().add(savedUser.getRole());

        return MyUserDetails.builder()
                .userModel(savedUser)
                .myPrincipal(myPrincipal)
                .attributes(user.getAttributes())
                .build();
    }
    private UserModel signupUser(OAuth2UserInfo oAuth2UserInfo, SocialProviderType socialProviderType){
        UserSignupProcDto dto = new UserSignupProcDto();
        dto.setProviderType(socialProviderType.name());
        dto.setUid(oAuth2UserInfo.getId());
        dto.setUpw("social");
        dto.setNm(oAuth2UserInfo.getName());
        dto.setPic(oAuth2UserInfo.getImageUrl());
        dto.setRole("USER"); // 로그인 후 권한 부여 방향

        int result = mapper.insUser(dto);

        UserModel entity = new UserModel();
        entity.setIuser(dto.getIuser());
        entity.setUid(dto.getUid());
        entity.setRole(dto.getRole());
        entity.setNm(dto.getNm());
        entity.setPic(dto.getPic());
        return entity;
    }
}
