package com.zeek.mp.mybatisplusfirst;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeek.mp.mybatisplusfirst.dao.EmployeeMapper;
import com.zeek.mp.mybatisplusfirst.dao.UserMapper;
import com.zeek.mp.mybatisplusfirst.entity.Employee;
import com.zeek.mp.mybatisplusfirst.entity.User;
import com.zeek.mp.mybatisplusfirst.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;
//
//    @Autowired
//    private EmployeeMapper employeeMapper;

    @Autowired
    private UserService userService;

//    @Test
//    public void testCommonInsert() {
//
//        // 初始化Employee对象
//        Employee employee = new Employee();
//        employee.setLastName("MP");
//        employee.setEmail("mp@atguigu.com");
//        employee.setGender(1);
//        employee.setAge(22);
//
//        employeeMapper.insert(employee);
//    }
//
//    @Test
//    public void selectAllEmployees() {
//        List<Employee> list = employeeMapper.selectList(null);
//        Assert.assertEquals(4, list.size());
//    }

    @Test
    public void testUserInsert() {
        User user = new User();
        user.setAge(55);
        user.setEmail("test55@test.com");
        user.setManagerId(1087982257332887553l);
        user.setName("test55");

        userService.insert(user);
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

    /**
     * 2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     *  name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void select2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨");
        queryWrapper.between("age", 20, 40);
        queryWrapper.isNotNull("email");

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::print);
    }

    /**
     * 3. 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     *     name like '王%' or age>=25 order by age desc,id asc
     */
    @Test
    public void select3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "王")
                .or().ge("age", 25)
                .orderByDesc("age")
                .orderByAsc("id");

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 4、创建日期为2019年2月14日并且直属上级为名字为王姓
     */
    @Test
    public void select4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<Long> managerIds = userMapper.selectList(queryWrapper.like("name", "王")).stream().map(User::getId).collect(Collectors.toList());
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date(create_time)", "2019-02-14")
                .in("manager_id", managerIds);

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void select4_1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date(create_time)", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '%王%'");

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }


}
