package com.zeek.mp.mybatisplusadvance;

import java.time.LocalDateTime;
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
public class FillTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setName("小王");
        user.setAge(35);

        int rows = userMapper.insert(user);
        System.out.println("影响行数: " + rows);
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(28);
//        user.setUpdateTime(LocalDateTime.now());

        int rows = userMapper.updateById(user);
        System.out.println(rows);
    }

}
