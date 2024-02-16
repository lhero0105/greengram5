package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Builder.Default
    @ToString.Exclude // toString시 배제
    // mappedBy 양방향, 빼면 테이블이 추가됩니다.
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST)
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList<>();
}
