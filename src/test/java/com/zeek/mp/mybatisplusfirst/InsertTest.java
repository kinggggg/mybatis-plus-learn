package com.zeek.mp.mybatisplusfirst;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeek.mp.mybatisplusfirst.dao.UserMapper;
import com.zeek.mp.mybatisplusfirst.entity.User;


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

    /**
     * 名字为王姓并且(年龄小于40或邮箱不为空)
     * where name like '王%' and (age < 40 or email is not null)
     */
    @Test
    public void selectByWrapper5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王") // 这个条件相当于 name like '%王' 作为第一部分
                // and 里边的相当于条件 age < 40 and email is not null 作为第二部分
                // 通过and进行连接两部分 最终的语句为 name like '王%' and (age < 40 or email is not null)
                .and(qw -> qw.lt("age", 40).or().isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者(年龄小于40并且年龄大于20并且邮箱不为空)
     * where name like '王%' or (age between 20 and 40 and email is not null)
     */
    @Test
    public void selectByWrapper6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王%")
                .or(qw -> qw.between("age", 20, 40).isNotNull("email"));

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * (年龄小于40或邮箱不为空)并且名字为王性
     * where (age < 40 or email is not null) and name like '王%'
     */
    @Test
    public void selectWrapper7() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(qw -> qw.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 年龄为30, 31, 34, 35
     * where age in (30, 31, 34, 35)
     */
    @Test
    public void selectWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条
     * where ... limit 1
     */
    @Test
    public void selectWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35))
                .last("limit 1");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     *  名字中包含雨并且年龄小于40
     *  name like '%雨%' and age < 40
     *
     *  只查询id和name字段
     */
    @Test
    public void selectByWrapperSupper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 第一种链式方式
//        queryWrapper.select("id", "name").like("name", "雨")
//                .lt("age", 40);
        // 第二种方式
        queryWrapper.select("id", "name").like("name", "雨");
        queryWrapper.lt("age", 40);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     *  名字中包含雨并且年龄小于40
     *  name like '%雨%' and age < 40
     *
     *  只查询id和name字段
     */
    @Test
    public void selectByWrapperSupper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name").like("name", "雨")
                .lt("age", 40);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void conditionTest() {
        condition("张三", "");
    }

    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 使用带condition条件的查询方法可以省略在代码中if非空判断
        queryWrapper.eq(StringUtils.isNotEmpty(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(email), "email", email);
        List<User> users = userMapper.selectList(queryWrapper);
    }

    @Test
    public void selectWrapperEntity() {

        User userEntity = new User();
        userEntity.setAge(20);
        userEntity.setName("张三");

        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        // QueryWrapper带构造参数的方式, 当生成SQL的时候在默认的情况是对实体中不为空(""和null)的属性生成where条件
        // 生成 WHERE name=? AND age=?
        // 并且带构造参数的构造器生成的where条件默认情况下是等值比较, 如要修改这个行为的话, 可以在实体属性上使用注解
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>(userEntity);
        // 使用了带构造参数的构造器后, 仍然可以自己通过API的方式进行条件查询, 并且两种方式互相不影响!
        queryWrapper2.like("name", "张三");
        userMapper.selectList(queryWrapper2);

    }
}
