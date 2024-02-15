package com.green.greengram4.user;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 처리")
    public ResVo postSignup(@RequestBody UserSignupDto dto) {
        log.info("dto: {}", dto);
        return service.signup(dto);
    }

    @PostMapping("/signin")
    @Operation(summary = "인증", description = "아이디/비번을 활용한 인증처리")
    public UserSigninVo postSignin(HttpServletResponse res
                                , @RequestBody UserSigninDto dto) {
        log.info("dto: {}", dto);
        return service.signin(res, dto);  //result - 1: 성공, 2: 아이디 없음, 3: 비밀번호 틀림
    }

    @PostMapping("/signout")
    public ResVo postSignout(HttpServletResponse res) {
        return service.signout(res);
    }

    @GetMapping("/refresh-token")
    public UserSigninVo getRefreshToken(HttpServletRequest req) {
        return service.getRefreshToken(req);
    }

    @GetMapping
    @Operation(summary = "유저 정보", description = "프로필 화면에서 사용할 프로필 유저 정보")
    public UserInfoVo getUserInfo(UserInfoSelDto dto) {
        log.info("dto: {}", dto);
        return service.getUserInfo(dto);
    }

    @PatchMapping("/firebase-token")
    public ResVo patchUserFirebaseToken(@RequestBody UserFirebaseTokenPatchDto dto) {
        return service.patchUserFirebaseToken(dto);
    }

    @PatchMapping("/pic")
    public UserPicPatchDto patchUserPic(@RequestPart MultipartFile pic) {
        return service.patchUserPic(pic);
    }

    //--------------- follow
    //ResVo - result: 1-following, 0-취소
    @PostMapping("/follow")
    public ResVo toggleFollow(@RequestBody UserFollowDto dto) {
        return service.toggleFollow(dto);
    }
}
