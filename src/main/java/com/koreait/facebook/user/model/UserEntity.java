package com.koreait.facebook.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private int iuser;
    private String provider;
    private String email;
    private String pw;
    private String nm;
    private String tel;
    private String authCd;
    private String mainProfile;
    private String regdt;
}
