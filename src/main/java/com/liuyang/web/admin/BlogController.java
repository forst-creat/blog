package com.liuyang.web.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyang.bean.Blog;
import com.liuyang.bean.Tag;
import com.liuyang.bean.Type;
import com.liuyang.bean.User;
import com.liuyang.service.BlogService;
import com.liuyang.service.TagService;
import com.liuyang.service.TypeService;
import com.liuyang.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author liuyang
 * @create 2022-08-11-22:19
 */
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

//    @ResponseBody
//    @GetMapping(value = "/blogs")
//    public String blogs(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
//                        @RequestParam(value = "size",defaultValue = "2") Integer size,
//                        Model model) {
//    public Map<String,Object> getAllBlogPage(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
//                        @RequestParam(value = "size",defaultValue = "2") Integer size,
//                        Model model) {
//        HashMap<String, Object> map = new HashMap<>();
//        Page<BlogQuery> page = blogService.getBlogType(new Page<>(pn, size));
//        model.addAttribute("types",typeService.list());
//        if (page.getRecords().size() == 0) {
//            map.put("code",400);
//        }else {
//            map.put("code",200);
//            map.put("data",page);
//        }
//        model.addAttribute("page",blogService.listBlog(pn,size,blog));
//        model.addAttribute("page",page);
//        return "admin/blogs";
//        return map;
//    }


    //列表数据展示
    @GetMapping(value = "/blogs")
    public String blogs(Model model,
                        @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                        @RequestParam(value = "size",defaultValue = "2") Integer size) {
        //分页查询数据
        Page<Blog> blogPage = new Page<>(pn,size);
        //分页查询结果,由于原来分页插件无法解决多对一自定义映射查询结果,故重写Page进行分页
        Page<Blog> page = blogService.getBlogAndTypeByStepOne(blogPage,null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /* Page<Blog> page = blogService.page(blogPage, null); ----->原来分页插件中的page()方法*/
        for (Blog blog : page.getRecords()) {
            blog.setUtime(dateFormat.format(blog.getUpdateTime()));
        }
        //当前页
        long current = page.getCurrent();
        //总页数
        long pages = page.getPages();
        //总记录数
        long total = page.getTotal();
        //经过分页后从数据库中查询出来的每页数据
        List<Blog> records = page.getRecords();
        //将数据展示给客户端
        model.addAttribute("page",page);
        //作为动态查询标签页中的可选内容
        List<Type> types = typeService.list();
        model.addAttribute("types",types);
        return "admin/blogs";
    }

    //动态查询
    @PostMapping(value = "/blogs/search")
    public String search(@RequestParam(value = "page") Integer pn,
                         @RequestParam(value = "size",defaultValue = "2") Integer size,
                         Model model,
                         BlogVo blog) {
        Page<Blog> page = blogService.listBlog(pn, size, blog);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Blog blogs : page.getRecords()) {
            blogs.setUtime(dateFormat.format(blogs.getUpdateTime()));
        }
        model.addAttribute("page",page);
        //动态局部刷新
        return "admin/blogs :: blogList";
    }

    //保存blog
    @GetMapping(value = "/blogs/input")
    public String input(Model model) {
        model.addAttribute("types",typeService.list());
        model.addAttribute("tags",tagService.list());
        model.addAttribute("blogs",new Blog());
        return "admin/blogs-input";
    }

    //新增blog
    @PostMapping(value = "/blogs")
    public String insert(Blog blog,RedirectAttributes attributes,HttpSession session) {
        blog.setUser((User) session.getAttribute("loginUser"));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        boolean b = false;
        if (blog.getId() == null ) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            b = blogService.insert(blog);
        }else {
            blog.setUpdateTime(new Date());
            b = blogService.updateById(blog);
        }

        if (b == false) {
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
            List<Tag> tags = blog.getTags();
            for (Tag tag : tags) {
                blogService.saveRelativity(blog.getId(),tag.getId());
            }
        }
        return "redirect:/blogs";
    }

    /**
     * 根据id获取对应的Blog、Type、Tag信息并跳转到编辑页面，将信息填充到页面上
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/blogs/{id}/input")
    public String getBlogById(@PathVariable("id") Long id,Model model) {
        Blog blog = blogService.listBlogById(id);
        blog.init();
        model.addAttribute("blog",blog);
        model.addAttribute("types",typeService.list());
        model.addAttribute("tags",tagService.list());
        return "admin/blogs-input";
    }

    /**
     * 根据id删除对应的博客
     * @param id
     * @param attributes
     * @return
     */

    @GetMapping(value = "/blogs/{id}/delete")
    public String deleteBlogById(@PathVariable("id") Long id,RedirectAttributes attributes) {
        blogService.removeById(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/blogs";
    }

}
