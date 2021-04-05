package com.livegoods.houses.service.impl;

import com.livegoods.houses.dao.HousesDao;
import com.livegoods.pojo.Houses;
import com.livegoods.houses.service.HousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HousesServiceImpl implements HousesService {
    @Autowired
    private HousesDao housesDao;

    @Override
    public List<Houses> getAll(){
        return housesDao.findAll();
    }
}
