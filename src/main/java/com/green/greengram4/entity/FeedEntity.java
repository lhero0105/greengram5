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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long ifeed;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @Builder.Default
    @ToString.Exclude // toString시 배제
    // mappedBy 양방향, 빼면 테이블이 추가됩니다.
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST)
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList();

    @ToString.Exclude
    @OneToMany(mappedBy = "feedEntity")
    private List<FeedFavEntity> feedFavList = new ArrayList<>();
}
