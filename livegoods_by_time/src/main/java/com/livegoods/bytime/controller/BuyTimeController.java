package com.livegoods.bytime.controller;

import com.livegoods.bytime.service.BuyTimeService;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BuyTimeController {
    @Autowired
    private BuyTimeService buyTimeService;

    @RequestMapping("/buytime")
    public Result<Object> getHouseBuyTime(String id){
        return buyTimeService.getHouseBuyTime(id);
    }
}
