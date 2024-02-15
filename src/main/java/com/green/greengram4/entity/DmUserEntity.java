package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "t_dm_user")
public class DmUserEntity {
    @EmbeddedId
    private DmUserIds dmUserIds;
    @ManyToOne
    @MapsId("idm")
    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private DmEntity dmEntity;

    @ManyToOne
    @MapsId("iuser")
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity iuser;
}
