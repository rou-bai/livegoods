package com.livegoods.dao.impl;

import com.livegoods.commons.pojo.Result;
import com.livegoods.dao.HotProductDao;
import com.livegoods.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class HotProductDaoImpl implements HotProductDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Items> findItems4HotProduct(Query query) {
        return mongoTemplate.find(query, Items.class);
    }
}
