package com.liuyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyang.bean.User;

/**
 * @author liuyang
 * @create 2022-08-11-9:59
 */
public interface UserService extends IService<User> {

    User checkUser(String username,String password);
}
