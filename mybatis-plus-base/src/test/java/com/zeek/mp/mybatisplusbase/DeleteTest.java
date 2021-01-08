package com.zeek.mp.mybatisplusbase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeek.mp.mybatisplusbase.dao.UserMapper;
import com.zeek.mp.mybatisplusbase.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        int rows = userMapper.deleteById(1346735055048802306L);
        System.out.println(rows);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "李一伟");

        int rows = userMapper.deleteByMap(columnMap);
        System.out.println(rows);
    }

    @Test
    public void deleteIds() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1094592041087729666L));
        System.out.println(rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAge, 31);

        int rows = userMapper.delete(lambdaQueryWrapper);
        System.out.println(rows);
    }
}
