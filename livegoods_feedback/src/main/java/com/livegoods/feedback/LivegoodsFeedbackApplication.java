package com.livegoods.feedback;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDistributedTransaction  //允许事务
public class LivegoodsFeedbackApplication {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsFeedbackApplication.class, args);
    }
}
