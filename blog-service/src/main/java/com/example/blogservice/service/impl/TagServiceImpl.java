package com.example.blogservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogservice.mapper.TagMapper;
import com.example.blogservice.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl extends ServiceImpl<TagMapper, Object> implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<String> queryAllTags() {
        return tagMapper.queryAllTags();
    }

    @Override
    public void addTag(String tagName) {
        tagMapper.addTag(tagName);
    }

    @Override
    public void deleteTag(String tagName) {
        tagMapper.deleteTag(tagName);
    }
}
