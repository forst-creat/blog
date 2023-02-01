package com.liuyang.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyang.bean.Blog;
import com.liuyang.vo.BlogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-11-6:44
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * blog的插入
     * @param blog
     * @return
     */
    boolean saveByBlog(Blog blog);

    /**
     * 查询所有的blog
     * @return
     */
    Page<Blog> getBlogAndTypeByStepOne(Page page, @Param("ew") QueryWrapper queryWrapper);

    Blog listBlogById(Long id);

    Page<Blog> listBlog(Page page);

    List<Blog> listBlogTop(Page page);

    /*
    * blog与tag是多对多映射，需要创建第三张关联表
    * */
    void saveRelativity(@Param("bid") Long bid,@Param("gid") Long gid);

    /**
     * 搜索框条件
     * @param page
     * @param query
     * @return
     */
    Page<Blog> listBlogByQuery(Page page,@Param("query") String query);

    /**
     * 博客浏览次数的增加
     * @param id
     */
    void addBlogViews(@Param("id") Long id);

}
