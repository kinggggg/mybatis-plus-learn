package com.zeek.mp.mybatisplusfirst;

import java.util.Arrays;
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

    @Test
    public void batch() {

        User user1 = new User();
        user1.setName("徐丽1");

        User user2 = new User();
        user2.setName("徐丽2");

        // 批量插入
        boolean b = userService.saveBatch(Arrays.asList(user1, user2));
        System.out.println(b);

    }

    @Test
    public void saveOrUpdate() {

        User user1 = new User();
        user1.setName("徐丽3");

        User user2 = new User();
        user1.setId(1347385597941022722L);
        user2.setName("徐力2");

        // 批量更新或者插入: 若实体中对应的主键值有值的话对该实体进行更新操作; 若实体中对应的主键值没有值的话, 对该实体进行插入操作
        boolean b = userService.saveOrUpdateBatch(Arrays.asList(user1, user2));
        System.out.println(b);
    }

    @Test
    public void chain() {

        List<User> list = userService.lambdaQuery().gt(User::getAge, 10).list();
        list.forEach(System.out::println);
    }

    @Test
    public void chain2() {

        boolean update = userService.lambdaUpdate().set(User::getAge, 30).eq(User::getAge, 33).update();
        System.out.println(update);
    }
}
