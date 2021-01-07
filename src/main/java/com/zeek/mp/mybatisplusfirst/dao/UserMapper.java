package com.zeek.mp.mybatisplusfirst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zeek.mp.mybatisplusfirst.entity.User;


public interface UserMapper extends BaseMapper<User> {

    // 按照如下方式定义就行. 官方文档也没有做很详细的解释, 参考 https://baomidou.com/guide/wrapper.html#%E4%BD%BF%E7%94%A8-wrapper-%E8%87%AA%E5%AE%9A%E4%B9%89sql
    @Select("select id, name from user ${ew.customSqlSegment}")
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    User selectAll00(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    List<User> selectAll0(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    // 使用注解方式. 与mybatis注解方式一样
    @Select("select id, name from user")
    List<User> selectAll2();

    // 使用注解方式. 与mybatis注解方式一样
    @Select("select id, name from user where name like '%${name}%'")
    List<User> selectAll3(@Param("name") String name);
}
