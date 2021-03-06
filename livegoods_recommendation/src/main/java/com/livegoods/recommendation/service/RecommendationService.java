package com.livegoods.recommendation.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;

public interface RecommendationService {
    /*
    根据城市查询热门推荐商品
     */
    Result<Items> getRecommendations(String city);
}
