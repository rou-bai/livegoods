package com.livegoods.houses.service.impl;

import com.livegoods.houses.dao.HousesDao;
import com.livegoods.pojo.Houses;
import com.livegoods.houses.service.HousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HousesServiceImpl implements HousesService {
    @Autowired
    private HousesDao housesDao;
    @Value("${livegoods.nginx.server}")
    private String nginxServer;
    @Value("${livegoods.details.imgMiddle}")
    private String imgMiddle;

    @Override
    public List<Houses> getAll(){
        return housesDao.findAll();
    }

    @Override
    public Houses getHouseById(String id){
        Houses houses = housesDao.findById(id);
        String[] imgs = houses.getImgs();
        for(int i=0; i<imgs.length; i++){
            imgs[i] = nginxServer + imgMiddle + imgs[i];
        }
        houses.setImgs(imgs);
        return houses;
    };
}
