package com.liuyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyang.bean.Tag;
import com.liuyang.bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-11-7:13
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    Tag findByName(@Param("name") String name);

    boolean save(Tag tag);

    List<Tag> listTag(@Param("ids") List<Long> ids);

    List<Tag> listTagTop(Page page);

    Tag TagAndBlog(@Param("gid") Integer id);
}
