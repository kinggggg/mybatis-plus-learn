package com.zeek.mp.mybatisplusbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeek.mp.mybatisplusbase.entity.User;

public interface UserService extends IService<User> {

    void insert(User user);
}
