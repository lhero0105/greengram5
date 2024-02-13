package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedInsDto p);
    List<FeedSelVo> selFeedAll(FeedSelDto p);
    int delFeed(FeedDelDto dto);
}