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
 * @create 2022-08-11-6:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("t_user")
public class User {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    private Date createTime;
    private Date updateTime;

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();
}
