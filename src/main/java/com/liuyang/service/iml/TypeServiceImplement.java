package com.liuyang.service.iml;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyang.bean.Blog;
import com.liuyang.bean.Type;
import com.liuyang.exception.NotFoundException;
import com.liuyang.mapper.TypeMapper;
import com.liuyang.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-12-0:50
 */
@Service
public class TypeServiceImplement extends ServiceImpl<TypeMapper,Type> implements TypeService{

    @Autowired
    TypeMapper typeMapper;

    @Override
    @Transactional
    public Type getTypeByName(String name) {
        return typeMapper.findByName(name);
    }

    @Override
    @Transactional
    public boolean insert(Type type) {
        return typeMapper.save(type);
    }

    @Override
    @Transactional
    public List<Type> listTypeTop(Integer size) {
        Page<Type> page = new Page<>(1, size);
        return typeMapper.listTypeTop(page);
    }

    @Override
    @Transactional
    public Type TypeAndBlog(Integer id) {
        return typeMapper.TypeAndBlog(id);
    }


}
