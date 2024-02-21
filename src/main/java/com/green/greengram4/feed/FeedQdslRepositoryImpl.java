package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.feed.model.FeedSelVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;
@Log4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<FeedSelVo> selFeedAll(Long loginIuser, Long targetIuser, Pageable pageable) {

        List<FeedEntity> feedList = jpaQueryFactory.select(feedEntity)
                .from(feedEntity)
                .orderBy(feedEntity.ifeed.desc())
                .fetch();

        return feedList.stream().map(item ->
                        FeedSelVo.builder()
                                .ifeed(item.getIfeed().intValue())
                                .location(item.getLocation())
                                .contents(item.getContents())
                                .createdAt(item.getCreatedAt().toString())
                                .writerIuser(item.getUserEntity().getIuser().intValue())
                                .writerNm(item.getUserEntity().getNm())
                                .writerPic(item.getUserEntity().getPic())
                                .build())
                .collect(Collectors.toList());
    }

    private BooleanExpression whereTargetUser(long targetIuser){
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);

    }
}
