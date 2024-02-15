package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user_follow")
public class UserFollowEntity extends CreatedAtEntity{
    @EmbeddedId
    // 복합키
    private UserFollowIds userFollowIds;

    // 외래키
    @ManyToOne(optional = false)
    @MapsId("fromIuser")
    @JoinColumn(name = "from_iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity fromUserEntity;

    @ManyToOne(optional = false)
    @MapsId("toIuser")
    @JoinColumn(name = "to_iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity toUserEntity;
}
