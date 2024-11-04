package com.example.blogservice.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Object> {

    List<String> queryAllTags();

    void addTag(String tagName);

    void deleteTag(String tagName);
}
