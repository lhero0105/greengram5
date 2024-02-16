package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 무조건 pk가 적용되어야 함
@Table(name = "t_feed_pics")
public class FeedPicsEntity extends CreatedAtEntity{

    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeed_pics;

    @ManyToOne
    @JoinColumn(name = "ifeed", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private FeedEntity feedEntity;

    @Column(length = 2100, nullable = false)
    private String pic;
}
