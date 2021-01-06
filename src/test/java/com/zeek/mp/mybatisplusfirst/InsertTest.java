package com.zeek.mp.mybatisplusfirst;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        user.setName("刘明强");
        user.setAge(231);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        // 注意: 在MP中默认的情况如果实体中的属性值为null的话, 当执行insert或者update操作的时候MP不会对值为null的列进行操作

        int rows = userMapper.insert(user);
        System.out.println("影响记录数:" + rows);
    }

    @Test
    public void selectById() {
        User user = userMapper.selectById(1088248166370832385L);
        System.out.println(user);
    }

    @Test
    public void selectByIds() {
        List<Long> ids = Arrays.asList(1088248166370832385L, 1087982257332887553L);
        List<User> users = userMapper.selectBatchIds(ids);
        users.stream().forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1088248166370832385L);
        map.put("name", "王天风");
        // map中的列为数据库表中的字段的名称, 而不是实体中的属性名称
        // 生成的SQL中的如下 WHERE name = ? AND id = ?
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void selectAll() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
