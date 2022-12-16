package com.liuyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyang.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuyang
 * @create 2022-08-11-7:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    User getBlogAndUserByStepTwo(@Param("user_id") Long id);
}
