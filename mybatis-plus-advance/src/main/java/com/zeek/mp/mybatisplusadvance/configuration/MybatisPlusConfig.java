package com.zeek.mp.mybatisplusadvance.configuration;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-07
 */
@Configuration
public class MybatisPlusConfig {

    public static ThreadLocal<String> myTableName = new ThreadLocal<>();

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

    /**
     * MP也提供了多租户场景下CRUD的实现
     * 在某些系统中需要支持多租户的场景, 而实现多租户的场景可以使用下面三种形式:
     * 1. 针对每一个租户使用不同数据库进行数据区分
     * 2. 针对每一个租户使用同一个数据库中不同的表进行数据区分
     * 3. 针对每一个租户使用同一个数据同一张表中的特定字段做数据区分
     *
     * 而MP提供了针对第3中形式下的支持
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//
//        ArrayList<ISqlParser> sqlParserList = new ArrayList<>();
//        TenantSqlParser tenantSqlParser = new TenantSqlParser();
//        tenantSqlParser.setTenantHandler(new TenantHandler() {
//
//            /**
//             * 用于指定多租户字段的值
//             *
//             * 实际的情况下应该是动态的, 例如从Session获取的, 这里为了演示为写死的
//             *
//             */
//            @Override
//            public Expression getTenantId() {
//                return new LongValue(1088248166370832385L);
//            }
//
//            // 指明用于区分多租户的字段名称, 是表中的字段名称不是实体的属性名称
//            @Override
//            public String getTenantIdColumn() {
//                return "manager_id";
//            }
//
//            /**
//             * 多哪些表进行多租户形式的查询
//             * 返回false表示要进行多租户的查询, 返回true不进行
//             * 这里也可以是动态的, 这里为了演示进行了写死
//             */
//            @Override
//            public boolean doTableFilter(String tableName) {
//                return false;
//            }
//        });
//
//        sqlParserList.add(tenantSqlParser);
//        paginationInterceptor.setSqlParserList(sqlParserList);
//
//        /**
//         * 通常下所有的查询语句都需要进行租户条件过滤, 也就是在where条件中加上租户信息的过滤条件
//         * 在某些特殊的情况下不需要加入租户的过滤条件! 可以通过下面的形式进行配置
//         */
//        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
//
//            // 返回false表示进行租户条件过滤
//            @Override
//            public boolean doFilter(MetaObject metaObject) {
//                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
//                if ("com.zeek.mp.mybatisplusadvance.dao.UserMapper.selectById".equals(ms.getId())) {
//                    return true;
//                }
//
//                return false;
//            }
//        });
//
//        return paginationInterceptor;
//    }

    /**
     * MP动态表明解析配置
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        ArrayList<ISqlParser> sqlParserList = new ArrayList<>();

        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>();
        tableNameHandlerMap.put("user", new ITableNameHandler() {
            @Override
            public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
                return myTableName.get();
            }
        });
        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);

        sqlParserList.add(dynamicTableNameParser);

        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }

}
