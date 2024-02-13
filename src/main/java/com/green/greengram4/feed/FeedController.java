package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Tag(name = "피드 API", description = "피드 관련 처리")
public class FeedController {
    private final FeedService service;

    @PostMapping
    @Operation(summary = "피드 등록", description = "피드 등록 처리")
    public FeedPicsInsDto postFeed(@RequestPart(required = false) List<MultipartFile> pics, @RequestPart FeedInsDto dto) {
        dto.setPics(pics);
        return service.postFeed(dto);
    }

    @GetMapping
    @Operation(summary = "피드 리스트", description = "전체 피드 리스트, 특정 사용자 프로필 화면에서 사용할 피드 리스트, 한 페이지 30개 피드 가져옴" +
            "<br><br>page: 페이지<br>loginedIuser: 로그인한 유저 pk")
    public List<FeedSelVo> getFeedAll(HttpServletRequest req, FeedSelDto dto) {
        log.info("dto: {}", dto);
        //return service.getFeedAll(dto);
        HttpSession session = req.getSession(false);
        if(session != null) {
           int iuser = (int)session.getAttribute("loginUserPk");
           log.info("iuser {}", iuser);
        }

        List<FeedSelVo> list = service.getFeedAll(dto);
        log.info("list: {}", list);
        return list;
    }

    @DeleteMapping
    public ResVo delFeed(FeedDelDto dto) {
        log.info("dto: {}", dto);
        return service.delFeed(dto);
    }


    @GetMapping("/fav")
    @Operation(summary = "좋아요 toggle", description = "toggle로 처리함<br>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 처리: result(1), 좋아요 취소: result(2)")
    })
    public ResVo toggleFeedFav(FeedFavDto dto) {
        log.info("dto : {}", dto);
        return service.toggleFeedFav(dto);
    }
}
