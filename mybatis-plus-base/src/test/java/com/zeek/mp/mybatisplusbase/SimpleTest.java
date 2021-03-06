package com.zeek.mp.mybatisplusbase;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeek.mp.mybatisplusbase.dao.UserMapper;
import com.zeek.mp.mybatisplusbase.entity.User;
import com.zeek.mp.mybatisplusbase.service.UserService;


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
        queryWrapper.likeRight("name", "王")
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
//        List<Long> managerIds = userMapper.selectList(queryWrapper.like("name", "王")).stream().map(User::getId).collect(Collectors.toList());
//        queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("date(create_time)", "2019-02-14")
//                .in("manager_id", managerIds);

        // 视频中的形式
        queryWrapper.apply("date(create_time) = {0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '%王%'");
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

    /**
     * 5、名字为王姓并且（年龄小于40或邮箱不为空）
     *     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void select5() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "王%")
                .and(wrapper -> wrapper.lt("age", 40).or().isNotNull("email"));

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     *     name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    public void select6() {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, "王%")
                // 默认情况下每个条件condition之间的关系为and, 如果需要为or的话, 需要显示指定
                .or(wrapper -> wrapper.lt(User::getAge, "40").gt(User::getAge, 20).isNotNull(User::getEmail));

        List<User> list = userMapper.selectList(lambdaQueryWrapper);
        list.forEach(System.out::println);
    }


    /**
     * 7、（年龄小于40或邮箱不为空）并且名字为王姓
     *     (age<40 or email is not null) and name like '王%'
     */
    @Test
    public void select7() {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.and(wrapper -> wrapper.lt(User::getAge, "40").or().isNotNull(User::getEmail))
                .like(User::getName, "王%");

        List<User> list = userMapper.selectList(lambdaQueryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 8、年龄为30、31、34、35
     *     age in (30、31、34、35)
     */
    @Test
    public void select8() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(User::getAge, Arrays.asList(30, 31, 34, 35));

        List<User> list = userMapper.selectList(lambdaQueryWrapper);
        list.forEach(System.out::println);

        // 返回指定字段
        list = userMapper.selectList(lambdaQueryWrapper.select(User::getId, User::getName));
        list.forEach(System.out::println);
    }

    /**
     * limit 1
     */
    @Test
    public void select9() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<User> list = userMapper.selectList(lambdaQueryWrapper.orderByDesc(User::getId).last("limit 1"));
        list.forEach(System.out::println);
    }
}
