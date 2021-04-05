package com.livegoods.houses.service.impl;

import com.livegoods.houses.dao.HousesDao;
import com.livegoods.pojo.Houses;
import com.livegoods.houses.service.HousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 增加缓存，redis。
     * MongoDB是一个高效的NoSQL数据库，查询效率非常高。
     * 增加缓存的目的，是减少MongoDB压力。减少代码压力。
     * 虽然Redis效率确实比MongoDB高一点，很多时候，这点性能提升，不足以
     * 屏蔽缓存的各种问题。如：缓存穿透、雪崩、击穿。
     * @param id
     * @return
     */

    @Override
    @Cacheable(cacheNames = "livegoods:details", key="'getHouseById(' + #id + ')'")
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
