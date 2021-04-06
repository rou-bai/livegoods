package com.livegoods.buyaction.controller;

import com.livegoods.buyaction.service.BuyActionService;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BuyActionController {
    @Autowired
    private BuyActionService buyActionService;

    @RequestMapping("/buyaction")
    public Result<Object> buyTime(String id, String user){
        return buyActionService.buyAction(id, user);
    }
}

