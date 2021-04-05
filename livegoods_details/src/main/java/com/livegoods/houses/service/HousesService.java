package com.livegoods.houses.service;

import com.livegoods.pojo.Houses;

import java.util.List;

public interface HousesService {
    /*
    查询全部房屋数据
     */
    List<Houses> getAll();

    /*
    查询房屋详情
     */
    Houses getHouseById(String id);
}
