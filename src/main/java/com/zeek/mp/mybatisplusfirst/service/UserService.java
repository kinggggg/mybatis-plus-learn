package com.zeek.mp.mybatisplusfirst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeek.mp.mybatisplusfirst.entity.User;

public interface UserService extends IService<User> {

    void insert(User user);
}
