package com.livegoods.houses.dao.impl;

import com.livegoods.houses.dao.HousesDao;
import com.livegoods.pojo.Houses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HousesDaoImpl implements HousesDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Houses> findAll(){
        return mongoTemplate.findAll(Houses.class);
    }

    @Override
    public Houses findById(String id){
        return mongoTemplate.findById(id, Houses.class);
    }
}
