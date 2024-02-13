package com.green.greengram4.security;

import com.green.greengram4.security.oauth2.CustomOAuth2UserService;
import com.green.greengram4.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.green.greengram4.security.oauth2.OAuth2AuthenticationRequestBasedOnCookieRepository;
import com.green.greengram4.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationRequestBasedOnCookieRepository oAuth2AuthenticationRequestBasedOnCookieRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable())
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                          "/api/feed"
                                        , "/api/feed/comment"
                                        , "/api/dm"
                                        , "/api/dm/msg"
                                        ).authenticated()
                        // 인증 한 사람들만 할 수 있습니다.
                                        .requestMatchers(HttpMethod.POST, "/api/user/signout"
                                                                        , "/api/user/follow"
                                        ).authenticated()
                        // post는 로그인이 되어야 합니다.
                                        .requestMatchers(HttpMethod.GET, "/api/user").authenticated()
                        // get은 로그인이 되어야 합니다.
                                        .requestMatchers(HttpMethod.PATCH, "/api/user/pic").authenticated()
                                        .requestMatchers(HttpMethod.GET, "/api/feed/fav").hasAnyRole("ADMIN")
                        // get으로 들어오는데 ADMIN이면 사용 가능
                                        .anyRequest().permitAll()
                )
                // 필터를 중간에 끼워 넣습니다.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 익셉션 핸들러로 커스터마이징 한 에러 사용
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .oauth2Login(oauth2 -> oauth2.authorizationEndpoint(auth ->
                        auth.baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(oAuth2AuthenticationRequestBasedOnCookieRepository)
                        ).redirectionEndpoint(redirection -> redirection.baseUri("/*/oauth2/code/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
