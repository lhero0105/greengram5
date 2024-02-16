package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@MappedSuperclass
// 무조건 pk가 적용되어야 함
@Table(name = "t_feed_comment")
public class FeedCommentEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeedComment;

    @ManyToOne
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity iuser;

    @ManyToOne
    @JoinColumn(name = "ifeed", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private FeedEntity ifeed;

    @Column(length = 500, nullable = false)
    private String comment;
}
