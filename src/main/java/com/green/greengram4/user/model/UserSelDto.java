package com.green.greengram4.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelDto {
    private String uid;
    private String providerType;
    private int iuser;
}
