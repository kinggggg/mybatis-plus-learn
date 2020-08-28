package com.zeek.mp.mybatisplusfirst.service;

import com.zeek.mp.mybatisplusfirst.dao.UserMapper;
import com.zeek.mp.mybatisplusfirst.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void insert(User user) {
        userMapper.insert(user);
        methodB();
    }

    public void methodB() {
        int i = 1 / 0;
    }
}
