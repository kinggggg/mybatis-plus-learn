package com.zeek.mp.mybatisplusadvance;

import jdk.nashorn.internal.ir.CallNode;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zeek.mp.mybatisplusadvance.dao.UserMapper;
import com.zeek.mp.mybatisplusadvance.entity.User;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicDeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        /**
         * 逻辑删除时MP生成的SQL为update语句
         * ==>  Preparing: UPDATE user SET deleted=1 WHERE id=? AND deleted=0
         * ==> Parameters: 1094592041087729666(Long)
         * <==    Updates: 1
         *
         * MP只能更改逻辑未删除的数据我逻辑已删除
         */
        int rows = userMapper.deleteById(1094592041087729666L);
        System.out.println("影响行数: " + rows);
    }

    @Test
    public void selectList() {
        /**
         * 当配置了逻辑删除的功能后, 当再进行查询的时候MP会自动在where中加入逻辑未删除的条件
         * SELECT id,name,age,email,manager_id,create_time,update_time,version,deleted FROM user WHERE deleted=0
         */
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(26);

        /**
         * 同样更新时也只能更新逻辑未删除的记录
         * UPDATE user SET age=? WHERE id=? AND deleted=0
         */
        int rows = userMapper.updateById(user);
        System.out.println(rows);
    }

    @Test
    public void mySelectList() {
        /**
         * 自定义的SQL查询默认的情况下在where中MP不会自动加上逻辑删除的条件, 此时就需要程序员自己加
         */
        List<User> users = userMapper.mySelectList(Wrappers.<User>lambdaQuery().gt(User::getAge, 25));
        users.forEach(System.out::println);
    }
}
