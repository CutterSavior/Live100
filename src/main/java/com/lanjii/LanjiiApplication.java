package com.lanjii;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lanjii.dao")
@EnableScheduling
public class LanjiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanjiiApplication.class, args);
    }

}
