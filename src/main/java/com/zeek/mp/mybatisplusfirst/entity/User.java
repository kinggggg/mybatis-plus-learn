package com.zeek.mp.mybatisplusfirst.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2020-08-18
 */
@Data
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

}
