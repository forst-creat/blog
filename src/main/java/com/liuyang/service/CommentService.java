package com.liuyang.service;

import com.liuyang.bean.Comment;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-12-28-22:07
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    boolean saveComment(Comment comment);

}
