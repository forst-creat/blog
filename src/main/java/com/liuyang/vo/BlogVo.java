package com.liuyang.vo;

import com.liuyang.bean.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyang
 * @create 2022-08-16-6:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlogVo {

    private String title;
    private Long typeId;
    private boolean recommend;
}
