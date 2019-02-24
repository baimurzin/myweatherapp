package com.baimurzin.myweatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyweatherappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyweatherappApplication.class, args);
    }

}
