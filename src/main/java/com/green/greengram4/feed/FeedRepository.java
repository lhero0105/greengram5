package com.green.greengram4.feed;


import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<FeedEntity, Long>, FeedQdslRepository { // 두번 di하지 않아도됨
    @EntityGraph(attributePaths = {"userEntity"})
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);
    // 쿼리메소드(리파지토리 메소드) where절 부터 쮹 작성하면 됩니다.
}
