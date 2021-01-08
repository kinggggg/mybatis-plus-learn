package com.zeek.mp.mybatisplusbase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @ClassName Employee
 * @Description
 * @Author liweibo
 * @Date 2020/8/25 11:47 下午
 * @Version v1.0
 **/
@Data
@TableName(value = "tbl_employee")
public class Employee {

    @TableId(type=IdType.AUTO)
    private Integer id;

    private String lastName;

    private String email;

    private Integer gender;

    private Integer age;
}
