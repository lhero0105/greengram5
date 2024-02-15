package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
// 자식들에게 전달 가능
@EntityListeners(AuditingEntityListener.class)
public class CreatedAtEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
