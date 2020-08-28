package com.zeek.mp.mybatisplusfirst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.zeek.mp.mybatisplusfirst.dao")
@SpringBootApplication(scanBasePackages = { "com.zeek.mp.mybatisplusfirst" })
public class MybatisPlusFirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusFirstApplication.class, args);
    }

}
