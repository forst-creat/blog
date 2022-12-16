package com.liuyang.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-11-6:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("t_type")
public class Type {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @NotBlank(message="分类名称不能为空")
    private String name;

    @TableField(exist = false)
    private List<Blog> blogs;
}
