package com.liuyang.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyang.bean.Tag;
import com.liuyang.bean.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-13-1:53
 */
public interface TagService extends IService<Tag>{
    Tag getTypeByName(String name);

    boolean insert(Tag tag);

    List<Tag> listTag(String ids) ;

    List<Long> converToList(String ids);

    List<Tag> listTagTop(Integer size);

    Tag TagAndBlog(Integer id);
}
