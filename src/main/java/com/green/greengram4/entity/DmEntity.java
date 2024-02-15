package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_dm")
public class DmEntity extends CreatedAtEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long idm;

    @Column(length = 2000, name = "last_name")
    private String lastNsg;

    @Column(name = "last_msg_at")
    @LastModifiedDate
    // java단에서 자동으로 마지막메세지가 들어가도록 합니다.
    private LocalDateTime lastMsgAt;
}
