package com.liuyang.web.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyang.bean.Tag;
import com.liuyang.bean.Type;
import com.liuyang.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-13-1:50
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String list(@RequestParam(value = "pn",defaultValue = "01") Integer pn, Model model) {

        List<Tag> tags = tagService.list();
//        model.addAttribute("tags",tags);

        //分页数据
        Page<Tag> tagPage = new Page<>(pn, 2);
        //分页查询
        Page<Tag> page = tagService.page(tagPage, null);
        long current = page.getCurrent();  //当前页数
        long pages = page.getPages();   //总页数
        long total = page.getTotal();  //总记录数
        boolean previous = page.hasPrevious();//是否有上一页
        boolean next = page.hasNext();//是否有下一页
        List<Tag> records = page.getRecords();//查出的数据
        model.addAttribute("page",page);
        return "admin/tags";
    }

    /**
     * 进入新增页面
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     * 编辑进入新增页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag",tagService.getById(id));
        return "admin/tags-input";
    }

    /**
     * 插入操作
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTypeByName(tag.getName());
        if (tag1 != null) {
            //rejectValue中的“name”参数必须和Type实体内中的name属性对应
            result.rejectValue("name","nameError","不能添加重复の分类");
        }

        //后端验证，防止通过前端传入name相同的分类
        if (result.hasErrors()) {
            return "admin/tags-input";
        }

        boolean b = tagService.insert(tag);
        if (b == false) {
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/tags";
    }

    /**
     * 更新操作
     * BindingResult 必须在 @Valid Type 后面，否则验证消息放不进去
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String post(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        //防止添加重复的分类
        Tag tag1 = tagService.getTypeByName(tag.getName());
        if (tag1 != null) {
            //rejectValue中的“name”参数必须和Type实体内中的name属性对应
            result.rejectValue("name","nameError","不能添加重复の分类");
        }

        //后端验证，防止通过前端传入name相同的分类
        if (result.hasErrors()) {
            return "admin/tags-input";
        }

        boolean b = tagService.updateById(tag);
        if (b == false) {
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/tags";
    }

    /**
     * 删除操作
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.removeById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/tags";
    }
}
