package com.zeek.mp.mybatisplusadvance;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zeek.mp.mybatisplusadvance.dao.UserMapper;
import com.zeek.mp.mybatisplusadvance.entity.User;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 配置了MP性能分析插件后, 在日志中可以看到每个SQL执行的时间
     *  Time：28 ms - ID：com.zeek.mp.mybatisplusadvance.dao.UserMapper.selectList
     * Execute SQL：SELECT id,name,age,email,manager_id,create_time,update_time,version FROM user WHERE deleted=0 AND name = '小红'
     */
    @Test
    public void select() {
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getName, "小红"));
        users.forEach(System.out::println);
    }
}
