package com.zeek.mp.mybatisplusadvance.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;


@Data
public class User extends Model<User> {

    @TableId
    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    // 自动填充配置
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 自动填充配置
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    // 版本
    private Integer version;

    // 逻辑删除标识(0: 未删除 1: 已删除)
    // 表明是逻辑删除字段
    @TableLogic
    // 除了可以全局指定逻辑删除的值外(在application.properties中指指定), 还可以针对具体的实体分别指定逻辑删除和逻辑未删除的标识, 如下
//    @TableLogic(value = "0", delval = "1")
    // 当查询的时候查询的字段不包含逻辑删除的字段
    @TableField(select = false)
    private Integer deleted;

}
