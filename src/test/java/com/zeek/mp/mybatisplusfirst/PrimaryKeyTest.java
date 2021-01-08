package com.zeek.mp.mybatisplusfirst;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeek.mp.mybatisplusfirst.entity.User;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrimaryKeyTest {

    /**
     * IdType.AUTO: 利用数据库主键自增策略
     * IdType.NONE: 跟随全局设置的主键策略. 全局的主键策略可以在配置文件中进行设置. 例如在application.properties文件中进行如下的全局主键策略的配置
     *      mybatis-plus:
     *         global-config:
     *          db-config:
     *           id-type: UUID
     *    并且如果即设置了全局主键策略又但对某个实体设置了局部的主键策略, 那么局部主键策略优先
     * IdType.INPUT: 主键的策略由用户自己来维护
     * 一下三种主键策略的前提是用户实体中没有设置主键的值, 若用户实体中设置了主键的值的话, 就使用用户实体中设置的主键的值
     * IdType.ID_WORKER: 采用雪花算法, 该主键策略的雪花算法的值是一个数字类型的, 因此在数据库中主键字段的类型要是数字类型的(例如bigint)
     * IdType.UUID: 采用UUID
     * IdType.ID_WORKER_STR: 与IdType.ID_WORKER不同之处在于, 该主键策略生成的是字符串类型的值, 因此数据库中主键的字段类型要设置成字符串类型的
     */
}
