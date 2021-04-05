package com.livegoods.houses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LivegoodsHousesApplication {
    public static void main(String[] args){
        SpringApplication.run(LivegoodsHousesApplication.class, args);
    }
}
