package com.zeek.mp.mybatisplusbase;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeek.mp.mybatisplusbase.entity.User;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {

    /**
     * 插入
     */
    @Test
    public void insert1() {
        User user = new User();
        user.setName("张草");
        user.setAge(21);
        user.setEmail("zc@test.com");
        user.setManagerId(1088248166370832385L);

        // MP的AR可以直接通过实体进行增删改查
        // MP的AR模式是由前提的:
        // 1. 与实体类也要有对应的Mapper(类似UserMapper.java)
        // 2. 实体类要继承Model对象
        boolean insert = user.insert();
        System.out.println(insert);
    }

    @Test
    public void selectById1() {
        User user = new User();

        User user1 = user.selectById(1347350615788908546L);
        // 查询出来的对象是一个新的对象
        System.out.println(user == user1);
        System.out.println(user1);
    }

    @Test
    public void selectById2() {
        User user = new User();
        user.setId(1347350615788908546L);

        User user1 = user.selectById();
        // 查询出来的对象是一个新的对象
        System.out.println(user == user1);
        System.out.println(user1);
    }

    @Test
    public void selectByWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, "张草");

        User user = new User();
        List<User> users = user.selectList(lambdaQueryWrapper);
        System.out.println(users);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1347350615788908546L);
        user.setName("张草草");

        boolean b = user.updateById();
        System.out.println(b);
    }

    @Test
    public void deleteById() {
        User user = new User();

        boolean b = user.deleteById(1347350615788908546L);
        System.out.println(b);
    }
}
