package com.liuyang.web;


//import com.liuyang.exception.NotFoundException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyang.bean.Blog;
import com.liuyang.bean.Tag;
import com.liuyang.bean.Type;
import com.liuyang.service.BlogService;
import com.liuyang.service.TagService;
import com.liuyang.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-10-23:01
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(Model model,@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                        @RequestParam(value = "size",defaultValue = "6") Integer size) {
        Page<Blog> page = new Page<>(pn,size);
        Page<Blog> blogPage = blogService.getBlogAndTypeByStepOne(page, null);
        //根据每个标签中的blog大小进行排序并且将排序分页后得到的Type对象返回给页面--------->排序未处理，后面进行
        List<Type> typeList = typeService.listTypeTop(10);
        Collections.sort(typeList, new Comparator<Type>() {
            @Override
            public int compare(Type type1, Type type2) {
                return -(type1.getBlogs().size()-type2.getBlogs().size());
            }
        });
        //与tag同理
        List<Tag> tagList = tagService.listTagTop(20);
        Collections.sort(tagList, new Comparator<Tag>() {
            @Override
            public int compare(Tag tag1, Tag tag2) {
                return -(tag1.getBlogs().size()-tag2.getBlogs().size());
            }
        });
        List<Blog> blogList = blogService.listBlogTop(10);
        model.addAttribute("blogPage",blogPage);
        model.addAttribute("typeList",typeList);
        model.addAttribute("tagList",tagList);
        model.addAttribute("blogList",blogList);
        return "index";
    }

    @PostMapping("/search")
    public String search(Model model,@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                         @RequestParam(value = "size",defaultValue = "6") Integer size,
                         @RequestParam(value = "query") String query) {
        Page<Blog> blogPage = blogService.listBlogByQuery(pn, size, query);
        model.addAttribute("blogPage",blogPage);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

}
