package com.liuyang;

import com.liuyang.bean.Blog;
import com.liuyang.bean.Comment;
import com.liuyang.bean.Tag;
import com.liuyang.bean.Type;
import com.liuyang.mapper.CommentMapper;
import com.liuyang.service.BlogService;
import com.liuyang.service.CommentService;
import com.liuyang.service.TagService;
import com.liuyang.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-12-01-22:53
 */
@SpringBootTest
public class TypeAndBlogTest {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void test01() {
        Type type = typeService.TypeAndBlog(1);
        System.out.println(type.getBlogs().size());
    }

    @Test
    public void test02() {
        List<Type> types = typeService.listTypeTop(9);
        System.out.println(types);
    }

    @Test
    public void test03() {
        Tag tag = tagService.TagAndBlog(3);
        System.out.println(tag);
    }

    @Test
    public void test04() {
        Blog blog = blogService.listBlogById(1L);
        List<Tag> tags = blog.getTags();
        for (Tag tag : tags) {
            System.out.println(tag);
        }
    }

    @Test
    public void test05() {
        List<Comment> comments = commentService.listCommentByBlogId(1L);
        for (Comment comment : comments) {
            List<Comment> replyComments = comment.getReplyComments();
            System.out.println(replyComments.size());
        }
    }

    @Test
    public void test06() {
        Comment parentCommentByStepOne = commentMapper.getParentCommentByStepOne(3L);
        System.out.println(parentCommentByStepOne);
    }



}
