package com.liuyang.service.iml;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyang.bean.Tag;
import com.liuyang.mapper.TagMapper;
import com.liuyang.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 * @create 2022-08-13-1:53
 */
@Service
public class TagServiceImplement extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    @Transactional
    public Tag getTypeByName(String name) {
        return tagMapper.findByName(name);
    }

    @Override
    @Transactional
    public boolean insert(Tag tag) {
        return tagMapper.save(tag);
    }

    @Override
    @Transactional
    public List<Tag> listTag(String ids) {
        return tagMapper.listTag(converToList(ids));
    }

    @Override
    @Transactional
    public List<Long> converToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i = 0 ; i<idarray.length ; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<Tag> listTagTop(Integer size) {
        Page<Tag> page = new Page<>(1,size);
        return tagMapper.listTagTop(page);
    }

    @Override
    @Transactional
    public Tag TagAndBlog(Integer id) {
        return tagMapper.TagAndBlog(id);
    }
}
