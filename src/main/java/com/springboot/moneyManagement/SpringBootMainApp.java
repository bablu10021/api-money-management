package com.springboot.moneyManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableTransactionManagement
@EntityScan("com.springboot.moneyManagement.entity")
public class SpringBootMainApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainApp.class, args);
    }
}