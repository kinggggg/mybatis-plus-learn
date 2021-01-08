package com.zeek.mp.mybatisplusfirst;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zeek.mp.mybatisplusfirst.entity.User;
import com.zeek.mp.mybatisplusfirst.service.UserService;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void selectOne() {

        // service中的getOne与mapper中getOne不同的是: service中的getOne若查询出的数据为多个话默认会返回第一条数据; 而mapper中若查询出的数据多余一条的话会报错
        // 查询的数据多余一条报错
//        User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 10));
        // 指定false后, 虽然查询出的数据为多条, 但是MP默认会获取第一条数据, 并且不报错
        User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 10), false);
        System.out.println(one);
    }
}
