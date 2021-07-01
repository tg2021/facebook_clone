package com.koreait.facebook.feed.model;

import lombok.Data;

@Data
public class FeedDTO {
    private int page;
    private int limit;
    private int iuserForMyFeed; // 나의 feed만 보고싶을 때 쓰는 로그인 유저 iuser값
    private int iuserForFav; // 나의 feed에 좋아요 헀는지 알기 위해 쓰는 로그인 유저 iuser값

    public int getStartIdx() {
        return (page - 1) * limit;
    }
}
