package com.zeek.mp.mybatisplusfirst.entity;

import lombok.Data;

import java.beans.Transient;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;


@Data
// 如果实体的名称与表的名称不一样的话, 通过@TableName注解进行对应
//@TableName("mp_user")
public class User extends Model<User> {

    // 默认的情况下mp会找名称为id的字段作为主键, 当表中没有名称为id的字段时, 直接插入会报错, 此时需要通过通过注解进行显示指定
    @TableId
//    private Long userId;
    private Long id;

    // 当数据库中的字段名称与实体中的字段名称不一样的时候, 可以通过@TableField记性关联
//    @TableField("realName")
    // 使用带构造参数的QueryWrapper时候默认情况下是等值比较, 可以使用下面的这个注解修改这个默认的行为
//    @TableField(condition = SqlCondition.LIKE)
    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    // 下面三种方式可以设置实体中的字段不数据库表中的字段不对应的情况
    // 通过下面三种方式的设置时, 实体对应的字段的值均不会持久化到数据库中(数据库对应的表中没有该字段)
    // transient 修饰符表明该字段不会持久化
    private transient String remark;
    // static 修饰符表明该字段也不会持久化
//    private static String remark;
    //默认的情况下exist为true, 设置为false也表明该字段不会被持久化
//    @TableField(exist = false)
//    private String remark;

}
