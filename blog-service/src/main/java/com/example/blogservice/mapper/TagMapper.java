package com.example.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;

import java.util.List;

public interface TagMapper extends BaseMapper<Object> {
    List<String> queryAllTags();

    void addTag(@Param("tagName") String tagName);

    void deleteTag(@Param("tagName") String tagName);
}
