package com.zeek.mp.mybatisplusadvance.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-07
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 从MP3.1.1开始就不需要配置个Bean了
     */
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }
}
