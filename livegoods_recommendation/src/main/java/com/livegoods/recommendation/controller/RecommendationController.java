package com.livegoods.recommendation.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;
import com.livegoods.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;
    @RequestMapping("/recommendation")
    public Result<Items> recommendation(String city){
        return recommendationService.getRecommendations(city);
    }
}
