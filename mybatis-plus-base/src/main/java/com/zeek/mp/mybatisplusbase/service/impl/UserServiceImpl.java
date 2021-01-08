package com.zeek.mp.mybatisplusbase.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeek.mp.mybatisplusbase.dao.UserMapper;
import com.zeek.mp.mybatisplusbase.entity.User;
import com.zeek.mp.mybatisplusbase.service.UserService;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void insert(User user) {

    }
}
