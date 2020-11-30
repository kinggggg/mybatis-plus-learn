package com.zeek.mp.mybatisplusfirst.entity;

import lombok.Data;

import java.beans.Transient;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
// 如果实体的名称与表的名称不一样的话, 通过@TableName注解进行对应
//@TableName("mp_user")
public class User {

    // 默认的情况下mp会找名称为id的字段作为主键, 当表中没有名称为id的字段时, 直接插入会报错, 此时需要通过通过注解进行显示指定
    @TableId
//    private Long userId;
    private Long id;

    // 当数据库中的字段名称与实体中的字段名称不一样的时候, 可以通过@TableField记性关联
//    @TableField("realName")
    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    // transient 修饰符表明该字段不会持久化
    private transient String remark;

}
