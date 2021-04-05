package com.livegoods.search.service.impl;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Houses;
import com.livegoods.search.dao.SearchDao;
import com.livegoods.search.feign.LivegoodsDetailsService;
import com.livegoods.search.pojo.House4ES;
import com.livegoods.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private LivegoodsDetailsService livegoodsDetailsService;
    @Value("${livegoods.search.defaultRows}")
    private int defaultRows;
    @Value("${livegoods.nginx.server}")
    private String nginxServer;
    @Value("${livegoods.img.middle}")
    private String imgMiddle;

    /*
    房屋查询
     */
    public Result<House4ES> search(String city, String content, int page){
        Map<String, Object> resultMap = searchDao.search(city, content, page, defaultRows);
        int pages = (int)resultMap.get("pages");
        List<House4ES> result = (List)resultMap.get("contents");

        Result<House4ES> res = new Result<>();
        for(House4ES house: result){
            house.setImg(nginxServer + imgMiddle + house.getImg());
        }
        res.setResults(result);
        res.setHasMore((pages - page) > 1);
        res.setStatus(200);
        return res;
    }

    /*
    初始化mongo房屋数据到es
     */
    @Override
    public boolean init(){
        List<Houses> houses = livegoodsDetailsService.getAll();
        searchDao.saveHouses(housesToES(houses));
        return true;
    }

    public List<House4ES> housesToES(List<Houses> houses){
        List<House4ES> house4ESList = new ArrayList<>();
        for(Houses house:houses){
            House4ES house4ES = new House4ES();
            house4ES.setId(house.getId());
            house4ES.setCity(house.getCity());
            String[] imgs = house.getImgs();
            //有图片时取第一张图片
            house4ES.setImg((imgs != null && imgs.length != 0) ? imgs[0] : "");
            house4ES.setPrice("<h3>" + house.getPrice() + "</h3>");
            house4ES.setRentType(house.getRentType());
            house4ES.setTitle(house.getTitle());
            Map<String, String> info = house.getInfo();
            String houseType = info.get("level")+" | "+info.get("type")+" - "+house.getHouseType();
            house4ES.setHouseType(houseType);
            house4ESList.add(house4ES);
        }
        return house4ESList;
    }

}
