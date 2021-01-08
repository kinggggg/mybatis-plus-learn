package com.zeek.mp.mybatisplusadvance.component;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 存在这样的一种需求:
 *  一般情况下数据库表中都会添加创建时间createTime和更新时间updateTime两个字段; 并且当插入数据库的时候
 *  需要设置创建时间的值, 同样当更新数据的时候需要设置更新时间的值
 *
 *  为了解决这个问题, 我们可以手动设置这两个字段的值, 但是效率比较低下
 *
 *  此时可以通使用MP自动填充配置的功能: 让MP来完成这个工作
 *
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 配置插入操作时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("insertFile");

        // createTime是实体中的属性名称不是数据库中的字段名称
        setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
    }

    // 配置更新操作时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("updateFill");

        // 同上
        setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);

    }
}
