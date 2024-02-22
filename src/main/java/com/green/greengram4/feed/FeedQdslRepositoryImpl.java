package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.feed.model.FeedSelVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;
import static com.green.greengram4.entity.QFeedFavEntity.feedFavEntity;
import static com.green.greengram4.entity.QFeedPicsEntity.feedPicsEntity;

@Log4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable) {

        JPAQuery<FeedEntity> jpaQuery = jpaQueryFactory.select(feedEntity)
                .from(feedEntity)
                .join(feedEntity.userEntity).fetchJoin()
                // 기본 inner
                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        if(dto.getIsFavList() == 1){
            jpaQuery.join(feedFavEntity)
                    .on(feedEntity.ifeed.eq(feedFavEntity.feedEntity.ifeed)
                    , feedFavEntity.userEntity.iuser.eq(dto.getLoginedIuser()));
            // 연관관계가 없는 애들끼리 연결 할 때 on
        }else {
            jpaQuery.where(whereTargetUser(dto.getTargetIuser()));
        }

        return jpaQuery.fetch();

/*        return feedList.stream().map(item ->
                        FeedSelVo.builder()
                                .ifeed(item.getIfeed().intValue())
                                .location(item.getLocation())
                                .contents(item.getContents())
                                .createdAt(item.getCreatedAt().toString())
                                .writerIuser(item.getUserEntity().getIuser().intValue())
                                // 영속성 컨텍스트에 있으면 셀렉x
                                .writerNm(item.getUserEntity().getNm())
                                .writerPic(item.getUserEntity().getPic())
                                .build())
                .collect(Collectors.toList());*/
    }

    @Override
    public List<FeedPicsEntity> selFeedPicsAll(List<FeedEntity> feedEntityList) {
        return jpaQueryFactory.select(Projections.fields(FeedPicsEntity.class // 원하는 부분만 가져오고 싶을 때
                , feedPicsEntity.feedEntity, feedPicsEntity.pic))
                .from(feedPicsEntity)
                .where(feedPicsEntity.feedEntity.in(feedEntityList)) // 값을 in으로 넣어줍니다.
                .fetch();
    }

    @Override
    public List<FeedFavEntity> selFeedFavAllByMe(List<FeedEntity> feedEntityList, long loginIuser) {
        return jpaQueryFactory.select(Projections.fields(FeedFavEntity.class
                , feedFavEntity.feedEntity))
                .from(feedFavEntity)
                .where(feedFavEntity.feedEntity.in(feedEntityList)
                        , feedFavEntity.userEntity.iuser.eq(loginIuser))
                .fetch();
    }

    private BooleanExpression whereTargetUser(long targetIuser){
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);

    }
}
