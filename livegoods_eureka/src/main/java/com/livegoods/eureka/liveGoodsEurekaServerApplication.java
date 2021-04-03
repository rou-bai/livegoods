package com.livegoods.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class liveGoodsEurekaServerApplication {
    public static void main(String[] args){
        SpringApplication.run(liveGoodsEurekaServerApplication.class, args);
    }
}
