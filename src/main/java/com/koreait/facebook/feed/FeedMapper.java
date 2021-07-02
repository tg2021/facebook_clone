package com.koreait.facebook.feed;

import com.koreait.facebook.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedEntity param);
    int insFeedImg(FeedImgEntity param);
    List<FeedDomain> selFeedList();
    List<FeedDomain2> selFeedList2(FeedDTO param);

    // fav
    int insFeedFav(FeedFavEntity pram);
    int delFeedFav(FeedFavEntity pram);

    // cmt
    int insFeedCmt(FeedCmtEntity param);
    List<FeedCmtDomain> selFeedCmtList(FeedCmtEntity param);
}
