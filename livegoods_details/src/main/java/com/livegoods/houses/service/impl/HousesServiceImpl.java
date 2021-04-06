package com.livegoods.houses.service.impl;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.livegoods.commons.pojo.Result;
import com.livegoods.houses.dao.HousesDao;
import com.livegoods.pojo.Houses;
import com.livegoods.houses.service.HousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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


    @Override
    @TccTransaction
    public Result<Houses> modifyHousesNum(Houses houses){
        housesDao.updateHousesNum(houses);
        Result<Houses> updateResult = new Result<>();
        updateResult.setStatus(200);
        updateResult.setResults(Arrays.asList(houses));
        return updateResult;
    }

    public Result<Houses> confirmModifyHousesNum(Houses houses){
        Result<Houses> updateResult = new Result<>();
        updateResult.setStatus(200);
        return updateResult;
    }
    // 取消，恢复原数据，库存+1。
    public Result<Houses> cancelModifyHousesNum(Houses houses){
        houses.setNums(houses.getNums() + 1);
        housesDao.updateHousesNum(houses);
        Result<Houses> updateResult = new Result<>();
        updateResult.setStatus(500);
        return updateResult;
    }
}
