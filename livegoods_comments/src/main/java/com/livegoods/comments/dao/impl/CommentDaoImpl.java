package com.livegoods.comments.dao.impl;

import com.livegoods.comments.dao.CommentsDao;
import com.livegoods.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentsDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    // 分页查询房屋评价集合
    @Override
    public List<Comments> findCommentsByHouseId(String id, int page, int rows){
        Query query = Query.query(
                Criteria.where("houseId").is(id)
        );
        query.with(
                PageRequest.of(page, rows, Sort.by(Sort.Direction.DESC, "_id"))
        );

        return mongoTemplate.find(query, Comments.class);

    }
    // 根据房屋主键，查询评论总数
    @Override
    public long findCommentsRowsByHouseId(String id){
        TypedAggregation<Comments> type = TypedAggregation.newAggregation(
                Comments.class,
                Aggregation.match(Criteria.where("houseId").is(id)),
                Aggregation.group().count().as("rows")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(type, Map.class);
        List<Map> list = results.getMappedResults();
        if(list == null || list.size() == 0){
            return 0L;
        }
        return Long.parseLong(list.get(0).get("rows").toString());
    }

}
