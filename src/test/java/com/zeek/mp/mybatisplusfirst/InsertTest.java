package com.zeek.mp.mybatisplusfirst;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeek.mp.mybatisplusfirst.dao.UserMapper;
import com.zeek.mp.mybatisplusfirst.entity.User;
import com.zeek.mp.mybatisplusfirst.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert1() {

        User user = new User();
        user.setName("向西");
        user.setAge(27);
        user.setManagerId(1088248166370832385l);
        user.setCreateTime(LocalDateTime.now());
        user.setRemark("我是一个备注");

        userMapper.insert(user);


    }

    @Test
    public void selectAll() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
