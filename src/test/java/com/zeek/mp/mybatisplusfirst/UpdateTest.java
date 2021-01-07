package com.zeek.mp.mybatisplusfirst;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.zeek.mp.mybatisplusfirst.dao.UserMapper;
import com.zeek.mp.mybatisplusfirst.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id进行更新
     */
    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setEmail("wtf@baomidou.com");

        int rows = userMapper.updateById(user);
        System.out.println("影响记录数: " + rows);

    }

    @Test
    public void updateByWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "李艺伟");

        User user = new User();
        user.setName("李一伟");
        int rows = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数: " + rows);
    }

    @Test
    public void updateByWrapper2() {
        // 与查询类型当向UpdateWrapper构造函数中传递一个实体时, 实体中不为null的属性将作为where条件
        User whereUser = new User();
        whereUser.setName("李一伟");

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);

        User user = new User();
        user.setName("李艺伟");
        int rows = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数: " + rows);
    }

    @Test
    public void updateByWrapper3() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // 若一个实体中的属性很多而有些情况下我们只需要更新一个属性的话, 如果用上面的更新方式需要new出一个实体对象, 这样感觉就很多余
        // 可以通过下面的方式进行少量字段的更新
        updateWrapper.eq("name", "李艺伟").set("name", "李一伟");

        int rows = userMapper.update(null, updateWrapper);
        System.out.println("影响记录数: " + rows);
    }

    @Test
    public void updateByWrapper4() {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getName, "李一伟");

        User user = new User();
        user.setName("李艺伟");
        int rows = userMapper.update(user, lambdaUpdateWrapper);
        System.out.println("影响记录数: " + rows);
    }

    @Test
    public void updateByWrapper5() {
        // 与查询一样, 更新操作也支持链式更新
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
                .eq(User::getName, "李艺伟").set(User::getName, "李一伟").update();
        System.out.println(update);
    }
}
