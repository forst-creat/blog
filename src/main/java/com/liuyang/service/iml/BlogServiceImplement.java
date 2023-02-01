package com.liuyang.service.iml;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyang.bean.Blog;
import com.liuyang.exception.NotFoundException;
import com.liuyang.mapper.BlogMapper;
import com.liuyang.service.BlogService;
import com.liuyang.util.MarkdownUtils;
import com.liuyang.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author liuyang
 * @create 2022-08-13-3:09
 */
@Service
public class BlogServiceImplement extends ServiceImpl<BlogMapper,Blog> implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    /**
     * 动态分页查询wrapper
     * @param
     * @param
     * @param blog
     * @return
     */
    @Override
    @Transactional
    public Page<Blog> listBlog(Integer pn, Integer size, BlogVo blog){
        Page<Blog> page = new Page<>(pn, size);
        QueryWrapper<Blog> blogWrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(blog.getTitle())) {
            blogWrapper.like("title",blog.getTitle());
        }
        if (blog.getTypeId() != null) {
            blogWrapper.eq("type_id",blog.getTypeId());
        }
        if (blog.isRecommend()) {
            blogWrapper.eq("recommend",blog.isRecommend());
        }
        return blogMapper.getBlogAndTypeByStepOne(page,blogWrapper);
    }

    @Override
    @Transactional
    public Page<Blog> listBlog(Page page) {
        return blogMapper.listBlog(page);
    }

    @Override
    @Transactional
    public boolean insert(Blog blog) {
        return blogMapper.saveByBlog(blog);
    }

    @Override
    @Transactional
    public void saveRelativity(Long bid, Long gid) {
         blogMapper.saveRelativity(bid, gid);
    }


    @Override
    @Transactional
    public Page<Blog> getBlogAndTypeByStepOne(Page page,QueryWrapper queryWrapper) {
        return blogMapper.getBlogAndTypeByStepOne(page,null);
    }

    @Override
    @Transactional
    public Blog listBlogById(Long id) {
        return blogMapper.listBlogById(id);
    }

    /**
     * 根据blog的id进行跳转同时增加浏览次数
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Blog getAndConvert(Long id) {
        Blog blog = blogMapper.listBlogById(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在!");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogMapper.addBlogViews(id);
        return b;
    }

    @Override
    @Transactional
    public List<Blog> listBlogTop(Integer size) {
        Page<Blog> page = new Page<>(1,size);
        return blogMapper.listBlogTop(page);
    }

    @Override
    @Transactional
    public Page<Blog> listBlogByQuery(Integer pn, Integer size, String query) {
        Page<Blog> page = new Page<>(pn,size);
        return blogMapper.listBlogByQuery(page,query);
    }

}
