package com.zeek.mp.mybatisplusfirst.entity;

import lombok.Data;

/**
 * @ClassName Employee
 * @Description
 * @Author liweibo
 * @Date 2020/8/25 11:47 下午
 * @Version v1.0
 **/
@Data
public class Employee {

    private Integer id;

    private String lastName;

    private String email;

    private Integer gender;

    private Integer age;
}
