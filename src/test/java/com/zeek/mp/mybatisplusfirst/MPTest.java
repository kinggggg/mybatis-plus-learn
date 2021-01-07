package com.zeek.mp.mybatisplusfirst;

import jdk.nashorn.internal.ir.CallNode;

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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.zeek.mp.mybatisplusfirst.dao.UserMapper;
import com.zeek.mp.mybatisplusfirst.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MPTest {

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

    @Test
    public void selectWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("age", 20);
        map.put("name", null);
        // 默认的情况下allEq对参数map中的值均作为where条件
        // 若map某个key对应的value为null的时候, 可以使用重载的allEq方法传递参数false, 这样生成的sql中就会排除value为null的条件
//        queryWrapper.allEq(map, false);

        // 对map中的条件进行过滤: 只有当key的值为name时候才拼装条件
        // 生成这样的SQL: SELECT id,name,age,email,manager_id,create_time FROM user WHERE name IS NULL
        queryWrapper.allEq((t, u) -> t.equals("name"), map);

        userMapper.selectList(queryWrapper);

    }

    @Test
    public void selectWrapperMaps() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("age").gt("age", 20);
        // 通过selectList的查询方式时, 此时我们只需要查询出age字段的值, 但是这种
        // 方式还是会查询出其他字段的值, 但是其他字段的值均为null, 这样的话效率比较低
        // [User(id=null, name=null, age=40, email=null, managerId=null, createTime=null, remark=null), User(id=null, name=null, age=25, email=null, managerId=null, createTime=null, remark=null), ...
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);

        // 通过selectMaps查询出的结果中只包含要查询的字段的值
        // [{age=40}, {age=25}, {age=28}, {age=31}, {age=32}, {age=231}]
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        System.out.println(maps);
    }

    @Test
    public void selectWrapperMaps2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 通过使用selectMaps查询数据库表中存在的字段
        queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id")
                .having("sum(age) < 50");

        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        System.out.println(maps);
    }

    @Test
    public void selectWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 通过使用selectMaps查询数据库表中存在的字段
        queryWrapper.select("id", "name").like("name", "雨");
        queryWrapper.lt("age", 40);

        // selectObjs返回要查询的数据库表字段的第一列的字段值
        // [1094590409767661570, 1094592041087729666] 可以看到虽然查询了name字段的值但是MP并没有返回
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        System.out.println(objects);
    }

    @Test
    public void selectWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨");
        queryWrapper.lt("age", 40);

        // 当使用selectCount的时候就不能再指定要查询的数据库字段了
        // SELECT COUNT( 1 ) FROM user WHERE name LIKE ? AND age < ?
        Integer result = userMapper.selectCount(queryWrapper);
        System.out.println(result);
    }

    @Test
    public void selectWrapperOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "刘红雨");
        queryWrapper.lt("age", 40);

        // selectOne查询的结果只能是一条, 如果是多条的话, 会报错
        User result = userMapper.selectOne(queryWrapper);
        System.out.println(result);
    }

    @Test
    public void selectLambda() {
        // 创建LambdaQueryWrapper的三种方式
//        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();
        // 使用LambdaQueryWrapper的好处在于可以防止误写, 因为QueryWrapper中字段的名称是字符串在编译期间无法识别
        lambdaQueryWrapper.like(User::getName, "雨")
                .lt(User::getAge, 40);

        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambda2() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();
        // LambdaQueryWrapper与QueryWrapper的使用基本一样
        lambdaQueryWrapper.like(User::getName, "雨")
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));

        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambda3() {
        List<User> users = new LambdaQueryChainWrapper<User>(userMapper).like(User::getName, "雨")
                .lt(User::getAge, 40).list();

        users.forEach(System.out::println);
    }

    @Test
    public void selectMy() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "刘红雨");
        queryWrapper.lt("age", 40);

        List<User> users = userMapper.selectAll(queryWrapper);
        System.out.println(users);

        System.out.println("==============");

        User user = userMapper.selectAll00(queryWrapper);
        System.out.println(user);

        System.out.println("==============");

        List<User> users0 = userMapper.selectAll0(queryWrapper);
        System.out.println(users0);

        System.out.println("==============");

        List<User> users1 = userMapper.selectAll2();
        System.out.println(users1);

        List<User> users2 = userMapper.selectAll3("雨");
        System.out.println(users2);
    }



}
