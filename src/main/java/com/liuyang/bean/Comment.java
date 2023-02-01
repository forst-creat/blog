package com.liuyang.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-11-6:48
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("t_comment")
public class Comment {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;

    //每个评论是针对于某个blog的
    @TableField(exist = false)
    private Blog blog;
    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();
    @TableField(exist = false)
    private Comment parentComment;
    //该字段用于在评论回复功能中辨别管理员(博主)
    @TableField(exist = false)
    private boolean adminComment;
}
