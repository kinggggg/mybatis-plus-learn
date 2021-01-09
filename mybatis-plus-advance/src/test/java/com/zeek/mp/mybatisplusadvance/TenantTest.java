package com.zeek.mp.mybatisplusadvance;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zeek.mp.mybatisplusadvance.configuration.MybatisPlusConfig;
import com.zeek.mp.mybatisplusadvance.dao.UserMapper;
import com.zeek.mp.mybatisplusadvance.entity.User;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 配置了MP多租户查询配置后, 此时再进行查询的话, MP会自动在where条件中加上 user.manager_id = 1088248166370832385
     * ==>  Preparing: SELECT id, name, age, email, manager_id, create_time, update_time, version FROM user WHERE user.manager_id = 1088248166370832385 AND deleted = 0 AND name = ?
     * ==> Parameters: 小红(String)
     */
    @Test
    public void select() {
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getName, "小红"));
        users.forEach(System.out::println);
    }

    /**
     * 配置了MP多租户查询配置后, MP也会为更新操作的where条件中加上 user.manager_id = 1088248166370832385
     * ==>  Preparing: UPDATE user SET age = ?, update_time = ? WHERE user.manager_id = 1088248166370832385 AND id = ? AND deleted = 0
     * ==> Parameters: 60(Integer), 2021-01-09T15:22:43.346(LocalDateTime), 1347738342274523138(Long)
     */
    @Test
    public void update() {
        User user = new User();
        user.setId(1347738342274523138L);
        user.setAge(60);

        int rows = userMapper.updateById(user);
        System.out.println(rows);
    }

    /**
     * 插入同理
     *
     * ==>  Preparing: INSERT INTO user (id, name, create_time, manager_id) VALUES (?, ?, ?, 1088248166370832385)
     * ==> Parameters: 1347806535261306882(Long), 李国民(String), null
     */
    @Test
    public void insert() {
        User user = new User();
        user.setName("李国民");

        userMapper.insert(user);
    }

    /**
     * 删除同理
     *
     * ==>  Preparing: UPDATE user SET deleted = 1 WHERE user.manager_id = 1088248166370832385 AND id = ? AND deleted = 0
     * ==> Parameters: 1347806535261306882(Long)
     */
    @Test
    public void delete() {
        userMapper.deleteById(1347806535261306882L);
   }

    @Test
    public void selectById() {
        // 测试MP动态表明解析
//        MybatisPlusConfig.myTableName.set("user_2021");

        User user = userMapper.selectById(1087982257332887553L);
        System.out.println(user);
    }

    /**
     * 自定义SQL查询没有进行租户条件过滤! 因为在自定义SQL查询上使用了注解@SqlParser(filter = true)表明不进行租户条件过滤
     *
     * ==>  Preparing: select * from user WHERE age > ?
     * ==> Parameters: 20(Integer)
     */
    @Test
    public void mySelectList() {
        userMapper.mySelectList(Wrappers.<User>lambdaQuery().gt(User::getAge, 20));
    }
}
