package com.liuyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyang.bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-11-7:13
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    Type findByName(@Param("name") String name);

    boolean save(Type type);

    Type getBlogAndTypeByStepTwo(@Param("type_id") Integer id);

    List<Type> listTypeTop(Page page);

    Type TypeAndBlog(@Param("tid") Integer id);

}
