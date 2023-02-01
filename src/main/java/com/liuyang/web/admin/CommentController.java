package com.liuyang.web.admin;

import com.liuyang.bean.Comment;
import com.liuyang.bean.User;
import com.liuyang.service.BlogService;
import com.liuyang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-12-28-21:58
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;



    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        System.out.println(comments);
        model.addAttribute("comments",comments);
        //动态局部刷新
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, RedirectAttributes attributes, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getById(blogId));
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        boolean b = commentService.saveComment(comment);
/*        if (b == false) {
            attributes.addFlashAttribute("message","评论失败");
        } else {
            attributes.addFlashAttribute("message","评论成功");
        }*/
        return "redirect:/comments/" + blogId;
    }


}
