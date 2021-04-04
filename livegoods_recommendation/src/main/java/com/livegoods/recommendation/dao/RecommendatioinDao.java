package com.livegoods.recommendation.dao;

import com.livegoods.pojo.Items;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface RecommendatioinDao {
    /*
    根据条件查询商品数据
     */
    List<Items> findItems4Recommendation(Query query);
}
