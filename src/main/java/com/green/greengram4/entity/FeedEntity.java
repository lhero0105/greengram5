package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
// 무조건 pk가 적용되어야 함
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeed;

    @ManyToOne
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity UserEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;
}
