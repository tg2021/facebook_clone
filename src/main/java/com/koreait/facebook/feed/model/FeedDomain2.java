package com.koreait.facebook.feed.model;

import lombok.Data;

import java.util.List;

@Data
public class FeedDomain2 extends FeedEntity{
    private String writer;
    private String mainProfile;
    private List<FeedImgEntity> imgList;
}
