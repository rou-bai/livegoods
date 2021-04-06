package com.livegoods.houses.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Houses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    /*
    修改商品库存
     */
    Result<Houses> modifyHousesNum(Houses houses);
}
