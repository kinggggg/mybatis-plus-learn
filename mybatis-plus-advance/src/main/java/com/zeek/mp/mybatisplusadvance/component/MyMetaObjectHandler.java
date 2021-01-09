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

    /**
     *  配置插入操作时自动填充
     *  默认情况下所有的插入操作都会被该方法拦截并进行判断.
     *  因此可能存在这样一种情况: 有些实体中并没有名称为createTime的字段, 此时就不需要自动填充
     *      针对这种情况, 可以先进行判断然后再进行填充
     *
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("insertFile");

        boolean has = metaObject.hasSetter("createTime1");
        if (has) {
            // createTime是实体中的属性名称不是数据库中的字段名称
            setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 配置更新操作时自动填充
     *
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        // 也可以判断当要自动填充的属性如果已经有值的话, 就不让MP再帮我们自己填充
        Object value = getFieldValByName("updateTime", metaObject);
        if (value == null) {
            System.out.println("updateFill");
            setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
