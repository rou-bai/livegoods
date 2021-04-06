package com.livegoods.lcn.tm;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
分布式
 */
@SpringBootApplication
@EnableTransactionManagerServer
public class LivegoodsTransactionManagerApplicatioin {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsTransactionManagerApplicatioin.class, args);
    }
}
