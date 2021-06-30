package com.koreait.facebook.feed.model;

import lombok.Data;

@Data
public class FeedDTO {
    private int page;
    private int limit;
    private int iuser;

    public int getStartIdx() {
        return (page - 1) * limit;
    }
}
