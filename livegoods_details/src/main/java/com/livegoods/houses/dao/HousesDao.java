package com.livegoods.houses.dao;

import com.livegoods.pojo.Houses;

import java.util.List;

public interface HousesDao {
    List<Houses> findAll();
    Houses findById(String id);

    void updateHousesNum(Houses houses);
}
