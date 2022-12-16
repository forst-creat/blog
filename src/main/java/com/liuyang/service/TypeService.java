package com.liuyang.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyang.bean.Type;
import org.apache.ibatis.annotations.Param;


import java.awt.print.Pageable;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-12-0:48
 */
public interface TypeService extends IService<Type> {


    Type getTypeByName(String name);

    boolean insert(Type type);

    List<Type> listTypeTop(Integer size);

    Type TypeAndBlog(Integer id);

}
