package com.zeek.mp.mybatisplusadvance.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

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

    /**
     * MP提供的乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * MP提供的性能分析插件
     */
    // 建议不用于生产环境
    @Profile({"dev", "test"})
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 格式化性能分析插件输出SQL, SQL看上去好看
        performanceInterceptor.setFormat(true);
        // 当超过下面设置的时间后, 慢SQL的查询就会停止, 程序会报错
//        performanceInterceptor.setMaxTime(5);
        return performanceInterceptor;
    }

}
