package com.liuyang.service.iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyang.bean.Comment;
import com.liuyang.mapper.CommentMapper;
import com.liuyang.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liuyang
 * @create 2022-12-28-22:10
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.getTopCommentsByStepOne(blogId);
        Collections.sort(comments,new Comparator<Comment>() {
            @Override
            public int compare(Comment comment1, Comment comment2) {
                //Long包装类中的静态compare()方法实际上是通过三目运算符进行比较来返回-1、0、1，避免了Long转int可能造成数据溢出的情况
                return Long.compare(comment1.getCreateTime().getTime(),comment2.getCreateTime().getTime());
            }
        });
        return eachComment(comments);
    }

    @Override
    @Transactional
    public boolean saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentMapper.selectById(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        //这里可以使用MyBatis-Plus中提供的insert方法
        return commentMapper.saveComment(comment);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                recursively(reply);
            }
        }
    }
}
