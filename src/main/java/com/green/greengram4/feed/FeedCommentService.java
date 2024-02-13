package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.FeedCommentInsDto;
import com.green.greengram4.feed.model.FeedCommentSelDto;
import com.green.greengram4.feed.model.FeedCommentSelVo;
import com.green.greengram4.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    public ResVo postFeedComment(FeedCommentInsDto dto) {

        //ifeed == 0 or comment == null or comment == ""
        //if(dto.getIfeed() == 0 || dto.getComment() == null || "".equals(dto.getComment())) {
        //  throw new RestApiException(FeedErrorCode.IMPOSSIBLE_REG_COMMENT);
        //}
        dto.setIuser(authenticationFacade.getLoginUserPk());
        int affectedRows = mapper.insFeedComment(dto);
        log.info("affectedRows: {}", affectedRows);
        return new ResVo(dto.getIfeedComment());
    }

    public List<FeedCommentSelVo> getFeedCommentAll(FeedCommentSelDto dto) {
        dto.setStartIdx(3);
        dto.setRowCount(999);
        return mapper.selFeedCommentAll(dto);
    }
}
