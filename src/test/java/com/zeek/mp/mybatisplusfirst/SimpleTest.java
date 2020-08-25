package com.zeek.mp.mybatisplusfirst;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeek.mp.mybatisplusfirst.dao.EmployeeMapper;
import com.zeek.mp.mybatisplusfirst.dao.UserMapper;
import com.zeek.mp.mybatisplusfirst.entity.Employee;
import com.zeek.mp.mybatisplusfirst.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2020-08-18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void selectAllEmployees() {

        List<Employee> list = employeeMapper.selectList(null);
        Assert.assertEquals(4, list.size());

    }

    @Test
    public void select() {

        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(5, list.size());

    }

    /**
     * 1、名字中包含雨并且年龄小于40
     * 	name like '%雨%' and age<40
     */
    @Test
    public void select1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name", "雨")
                .lt("age", 40);

        List<User> list = userMapper.selectList(queryWrapper);

        list.forEach(System.out::print);
    }
}
