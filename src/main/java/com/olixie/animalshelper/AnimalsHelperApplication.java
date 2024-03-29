package com.olixie.animalshelper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.olixie.animalshelper.mapper")
public class AnimalsHelperApplication {



    public static void main(String[] args) {
        SpringApplication.run(AnimalsHelperApplication.class, args);
    }

}
