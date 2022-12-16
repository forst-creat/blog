package com.liuyang.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-11-6:35
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("t_blog")
public class Blog {


    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views = 0;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;

    private Date createTime;
    @TableField(exist = false)
    private String ctime;
    private Date updateTime;
    @TableField(exist = false)
    private String utime;

    @TableField(exist = false)
    private Type type;

    @TableField(exist = false)
    private List<Tag> tags = new ArrayList<>();

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();

   @TableField(exist = false)
   private String tagIds;

   private String description;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }
}
