package com.livegoods.houses.controller;

import com.livegoods.pojo.Houses;
import com.livegoods.houses.service.HousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class HousesController {
    @Autowired
    private HousesService housesService;
    /*
    获取房屋全部mongo数据，初始化elasticsearch
     */
    @GetMapping("/getAll")
    public List<Houses> getAll(){
        return housesService.getAll();
    }
}
