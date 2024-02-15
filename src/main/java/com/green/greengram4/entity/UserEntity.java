package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.common.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
// 무조건 pk가 적용되어야 함
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = { "provider_type", "uid"}
        )
})

@Builder
@AllArgsConstructor
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iuser;

    // columnDefinition - 없는 속성 줄 떄 사용
    @Column(length = 10, name = "provider_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'LOCAL'")
    private ProviderTypeEnum providerType;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(length = 2100, name = "firebase_token")
    private String firebaseToken;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private RoleEnum role;
}
