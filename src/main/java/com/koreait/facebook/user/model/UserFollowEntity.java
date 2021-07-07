package com.koreait.facebook.user.model;

import lombok.Data;

@Data
public class UserFollowEntity {
    private int iuserMe;
    private int iuserYou;
    private String regdt;
}
