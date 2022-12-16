package com.liuyang.service.iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyang.bean.User;
import com.liuyang.mapper.UserMapper;
import com.liuyang.service.UserService;
import com.liuyang.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liuyang
 * @create 2022-08-11-10:00
 */
@Service
public class UserServiceImplement extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public User checkUser(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
