package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedCommentEntity;
import com.green.greengram4.entity.FeedEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedCommentRepository extends JpaRepository<FeedCommentEntity, Long> {
    @EntityGraph(attributePaths = {"userEntity"})
    List<FeedCommentEntity> findAllTop4ByFeedEntity(FeedEntity feedEntity);
    // 피드엔티티안에 있는 코멘트를 조건으로 오래된 글 4개만 가져옵니다.
}