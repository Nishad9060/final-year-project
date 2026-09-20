package com.footonomy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FootonomyBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootonomyBeApplication.class, args);
    }
}
