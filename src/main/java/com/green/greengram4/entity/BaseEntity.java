package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
// createdAt, updatedAt 둘 다 사용 시
@Data
@EntityListeners(AuditingEntityListener.class)
// 엔티티를 DB에적용하기전 AuditingEntityListener를 호출해 공통 처리
// 등록 수정 시 사용
public class BaseEntity extends CreatedAtEntity{

    @LastModifiedDate
    private LocalDateTime updatedAt;
    // currenttimestamp는 jpa가 알아서 해줍니다.
}
