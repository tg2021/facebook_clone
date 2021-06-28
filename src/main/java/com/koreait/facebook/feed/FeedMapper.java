package com.koreait.facebook.feed;

import com.koreait.facebook.feed.model.FeedEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {
    int insFeed(FeedEntity param);
    int insFeedImg(FeedEntity param);
}
