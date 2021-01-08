package com.zeek.mp.mybatisplusbase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zeek.mp.mybatisplusbase.dao")
@SpringBootApplication(scanBasePackages = {"com.zeek.mp.mybatisplusbase"})
public class MybatisPlusBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusBaseApplication.class, args);
    }

}
