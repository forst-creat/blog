package com.liuyang.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyang.bean.Blog;
import com.liuyang.vo.BlogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-13-3:08
 */
public interface BlogService extends IService<Blog> {

        boolean insert(Blog blog);

        void saveRelativity(Long tid,Long gid);

        Page<Blog> listBlog(Integer pn, Integer size, BlogVo blog);

        Page<Blog> listBlog(Page page);

        Page<Blog> getBlogAndTypeByStepOne(Page page, QueryWrapper queryWrapper);

        Blog listBlogById(Long id);

        Blog getAndConvert(Long id);

        List<Blog> listBlogTop(Integer size);

        Page<Blog> listBlogByQuery(Integer pn,Integer size,String query);

}
