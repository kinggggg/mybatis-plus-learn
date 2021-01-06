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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

    /**
     *  名字中包含雨并且年龄小于40
     *  name like '%雨%' and age < 40
     */
    @Test
    public void selectByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 也可以通过下面的方式定义queryWrapper
//        queryWrapper = Wrappers.query();

        // MP中的like已经自动包含了%%
        queryWrapper.like("name", "雨");
        queryWrapper.lt("age", 40);
        // 也可以通过链式调用的方式进行
//        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字总包含雨并且年龄大于等于20并且小于等于40并且email不为空
     * name like '%雨%' and age >= 20 and age <=40 and email is not null
     * name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        //第一种查询方式
//        queryWrapper.like("name", "雨")
//                .ge("age", 20)
//                .le("age", 40)
//                .isNotNull("email");
        // 第二种查询方式
        queryWrapper.like("name", "雨")
                .between("age", 20, 40)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者年龄大于等于25, 按照年龄降序排列, 年龄相同按照id升序排列
     * where name like '%王%' or age >=25 order by age desc, id asc
     */
    @Test
    public void selectByWrapper3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 注意: 默认的情况不同的查询条件之间是通过and连接的, 如果为or的话需要显示指定
        queryWrapper.likeRight("name", "雨")
                .or().ge("age", 25)
                .orderByDesc("age")
                .orderByAsc("id");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 创建日期为2019年2月14日并且直属上级为名字为王姓
     * where create_time = '2019-02-14' and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectByWrapper4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 使用apply方法执行sql中的函数
        // 使用inSql方法进行子查询
//        queryWrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", "2019-02-14")
//                .inSql("manager_id", "select id from user where name like '王%'");
        queryWrapper.apply("date_format(create_time, '%Y-%m-%d') = '2019-02-14'")
                .inSql("manager_id", "select id from user where name like '王%'");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }
}
