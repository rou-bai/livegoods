package com.livegoods.recommendation.service.impl;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;
import com.livegoods.recommendation.dao.RecommendatioinDao;
import com.livegoods.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    private RecommendatioinDao recommendatioinDao;

    @Value("${livegoods.nginx.server}")
    private String nginxServer;

    /**
     * 1、 查询条件匹配，如果所在城市的推荐商品>=4条，可以处理后返回。
     * 2、 如果查询条件匹配数据不足4条，从其他城市查询热门推荐商品补足。
     * 3、 如果所有城市的热门推荐商品不足4条，从当前城市的非热门推荐商品中补充。
     * 4、 如果所有城市热门推荐+当前城市非热门推荐商品总数不足4条，从其他城市非热门推荐商品中补充。
     * 5、 所有特例情况累加不足4条数据，返回所有数据。
     * @param city 查询条件
     * @return
     */

    @Override
    public Result<Items> getRecommendations(String city){
        Query query = Query.query(
                Criteria.where("city").is(city).and("recommendation").is(true)
        );
        query.with(
                PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "recommendationSort"))
        );
        List<Items> result = recommendatioinDao.findItems4Recommendation(query);
        if(result.size() < 4){
            query = Query.query(
                    Criteria.where("city").ne(city).and("recommendation").is(true)
            );
            query.with(
                    PageRequest.of(0, 4-result.size(), Sort.by(Sort.Direction.DESC, "recommendationSort"))
            );
            List<Items> otherCityRecommendations = recommendatioinDao.findItems4Recommendation(query);
            //添加到结果集中
            result.addAll(otherCityRecommendations);
            if(result.size() < 4){
                query = Query.query(
                        Criteria.where("city").is(city).and("recommendation").is(false)
                );
                query.with(
                        PageRequest.of(0, 4-result.size())
                );
                List<Items> notRecommendations = recommendatioinDao.findItems4Recommendation(query);
                //添加到结果集中
                result.addAll(notRecommendations);
                if(result.size() < 4){
                    query = Query.query(
                            Criteria.where("city").ne(city).and("recommendation").is(false)
                    );
                    query.with(
                            PageRequest.of(0, 4-result.size())
                    );
                    List<Items> otherRecommendations = recommendatioinDao.findItems4Recommendation(query);
                    //添加到结果集中
                    result.addAll(otherRecommendations);
                }
            }
        }
        for(Items i: result){
            i.setImg(nginxServer + i.getImg());
        }
        return new Result<>(200, result);
    };
}
