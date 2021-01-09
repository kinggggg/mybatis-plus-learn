package com.zeek.mp.mybatisplusadvance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zeek.mp.mybatisplusadvance.dao.UserMapper;
import com.zeek.mp.mybatisplusadvance.entity.User;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LockTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * MP提供的乐观锁机制只能适用于updateById的情况
     */
    @Test
    public void updateById() {
        int version = 1;
        User user = new User();
        user.setId(1347568809506054145L);
        user.setAge(48);
        user.setVersion(version);

        int rows = userMapper.updateById(user);
        System.out.println(rows);
    }

    /**
     * MP提供的乐观锁机制同样wrapper只能使用一次
     */
    @Test
    public void updateByWrapper() {

        int version = 2;
        User user = new User();
        user.setId(1347568809506054145L);
        user.setAge(58);
        user.setVersion(version);

        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().eq(User::getName, "小红");
        int rows = userMapper.update(user, queryWrapper);
        System.out.println(rows);

    }

    @Test
    public void updateByWrapper2() {

        int version = 3;
        User user = new User();
        user.setId(1347568809506054145L);
        user.setAge(58);
        user.setVersion(version);

        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().eq(User::getName, "小红");
        int rows = userMapper.update(user, queryWrapper);
        System.out.println(rows);

        int version2 = 4;
        User user2 = new User();
        user2.setId(1347568809506054145L);
        user2.setAge(68);
        user2.setVersion(version2);

        int rows2 = userMapper.update(user2, queryWrapper);
        System.out.println(rows2);

    }
}
