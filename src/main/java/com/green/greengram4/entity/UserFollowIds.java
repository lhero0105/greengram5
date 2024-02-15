package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Embeddable
@EqualsAndHashCode
public class UserFollowIds implements Serializable {
    @Column(name = "from_iuser")
    private Long fromIuser;

    @Column(name = "to_iuser")
    private Long toIuser;
}