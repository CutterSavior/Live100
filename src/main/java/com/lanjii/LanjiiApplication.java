package com.lanjii;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lanjii.dao")
public class LanjiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanjiiApplication.class, args);
    }

}
