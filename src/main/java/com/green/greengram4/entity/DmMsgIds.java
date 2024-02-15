package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Embeddable
@EqualsAndHashCode
public class DmMsgIds implements Serializable {
    private Long idm;
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long seq;
}
