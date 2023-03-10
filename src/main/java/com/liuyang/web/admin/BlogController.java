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


    //??????????????????
    @GetMapping(value = "/blogs")
    public String blogs(Model model,
                        @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                        @RequestParam(value = "size",defaultValue = "2") Integer size) {
        //??????????????????
        Page<Blog> blogPage = new Page<>(pn,size);
        //??????????????????,????????????????????????????????????????????????????????????????????????,?????????Page????????????
        Page<Blog> page = blogService.getBlogAndTypeByStepOne(blogPage,null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /* Page<Blog> page = blogService.page(blogPage, null); ----->????????????????????????page()??????*/
        for (Blog blog : page.getRecords()) {
            blog.setUtime(dateFormat.format(blog.getUpdateTime()));
        }
        //?????????
        long current = page.getCurrent();
        //?????????
        long pages = page.getPages();
        //????????????
        long total = page.getTotal();
        //?????????????????????????????????????????????????????????
        List<Blog> records = page.getRecords();
        //???????????????????????????
        model.addAttribute("page",page);
        //?????????????????????????????????????????????
        List<Type> types = typeService.list();
        model.addAttribute("types",types);
        return "admin/blogs";
    }

    //????????????
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
        //??????????????????
        return "admin/blogs :: blogList";
    }

    //??????blog
    @GetMapping(value = "/blogs/input")
    public String input(Model model) {
        model.addAttribute("types",typeService.list());
        model.addAttribute("tags",tagService.list());
        model.addAttribute("blogs",new Blog());
        return "admin/blogs-input";
    }

    //??????blog
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
            attributes.addFlashAttribute("message","????????????");
        }else {
            attributes.addFlashAttribute("message","????????????");
            List<Tag> tags = blog.getTags();
            for (Tag tag : tags) {
                blogService.saveRelativity(blog.getId(),tag.getId());
            }
        }
        return "redirect:/blogs";
    }

    /**
     * ??????id???????????????Blog???Type???Tag????????????????????????????????????????????????????????????
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
     * ??????id?????????????????????
     * @param id
     * @param attributes
     * @return
     */

    @GetMapping(value = "/blogs/{id}/delete")
    public String deleteBlogById(@PathVariable("id") Long id,RedirectAttributes attributes) {
        blogService.removeById(id);
        attributes.addFlashAttribute("message","????????????!");
        return "redirect:/blogs";
    }

}
