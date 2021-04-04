package com.livegoods.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;
import com.livegoods.service.HotProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HotProductController {
    @Autowired
    private HotProductService hotProductService;
    @GetMapping("/hotProduct")
    public Result<Items> getHotProduct(String city){
        return hotProductService.getHostProducts(city);
    }
}
