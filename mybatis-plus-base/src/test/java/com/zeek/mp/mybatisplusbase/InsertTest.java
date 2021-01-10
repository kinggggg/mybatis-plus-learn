package com.zeek.mp.mybatisplusbase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeek.mp.mybatisplusbase.dao.UserMapper;
import com.zeek.mp.mybatisplusbase.entity.User;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void insert1() {

        User user = new User();
        user.setName("刘明强005");
//        user.setAge(44);
        // 注意: 在MP中默认的情况如果实体中的属性值为null的话, 当执行insert或者update操作的时候MP不会对值为null的列进行操作
        // mybatis-plus:
        //  global-config:
        //    db-config:
        //      insert-strategy: not_null
        // 可以看到当插入的策略为not_null时, 只有实体中属性的值不为null的字段才参与插入生成的SQL为:
        /**
         * ==>  Preparing: INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
         * ==> Parameters: 1347376318936801281(Long), 刘明强003(String), 33(Integer)
         */
        int rows = userMapper.insert(user);
        System.out.println("影响记录数:" + rows);
    }

    @Test
    public void insert2() {

        User user = new User();
        user.setName("刘明强002");
        user.setAge(22);
        // 注意: 在MP中默认的情况如果实体中的属性值为null的话, 当执行insert或者update操作的时候MP不会对值为null的列进行操作
        // mybatis-plus:
        //  global-config:
        //    db-config:
        //      insert-strategy: ignored
        // 可以看到当插入的策略为 ignored 时(即忽略判断), 所有的属性都参与了插入操作, 实体中属性没有值的按照null插入
        /**
         * ==>  Preparing: INSERT INTO user ( id, name, age, email, manager_id, create_time ) VALUES ( ?, ?, ?, ?, ?, ? )
         * ==> Parameters: 1347375963331133442(Long), 刘明强002(String), 22(Integer), null, null, null
         */
        int rows = userMapper.insert(user);
        System.out.println("影响记录数:" + rows);
    }

    /**
     * 以上测试的是插入操作, 对于更新操作同样适用
     */
}
