package com.livegoods.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LivegoodsSearchApplication {
    public static void main(String[] args){
        SpringApplication.run(LivegoodsSearchApplication.class, args);
    }
}
