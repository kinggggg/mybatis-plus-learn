package com.zeek.mp.mybatisplusadvance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zeek.mp.mybatisplusadvance.dao")
@SpringBootApplication(scanBasePackages = {"com.zeek.mp.mybatisplusadvance"})
public class MybatisPlusAdvanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusAdvanceApplication.class, args);
    }

}
