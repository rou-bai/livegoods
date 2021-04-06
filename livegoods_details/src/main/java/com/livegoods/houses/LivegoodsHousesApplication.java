package com.livegoods.houses;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableDistributedTransaction   //允许事务
public class LivegoodsHousesApplication {
    public static void main(String[] args){
        SpringApplication.run(LivegoodsHousesApplication.class, args);
    }
}
