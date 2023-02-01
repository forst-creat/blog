package com.liuyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyang.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-11-7:13
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据博客id查找出对应页面的顶级评论节点
     * @param blogId
     * @return
     */
    List<Comment> getTopCommentsByStepOne(@Param("blogId") Long blogId);

    List<Comment> getReplyCommentByStepTwo(@Param("pid") Long pid);

    Comment getParentCommentByStepOne(@Param("cid") Long cid);

    /**
     * 保存游客的发送的评论信息
     * @param comment
     * @return
     */
    boolean saveComment(Comment comment);

    /**
     * 返回某个顶级评论节点的次级节点
     * @param comment
     * @return
     */
    List<Comment> listAllByReplyComments(Comment comment);
}
