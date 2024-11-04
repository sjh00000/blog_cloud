package com.example.blogservice.controller;

import com.example.blogservice.resp.Result;
import com.example.blogservice.service.BlogService;
import com.example.blogservice.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TagController {
    @Autowired
    private  TagService tagService;

    /**
     * 查询所有不重复的博客标签接口。
     */
    @GetMapping("/blog/tags")
    public Result queryAllTags() {
        // 执行查询，由于只关心标签，不需要分页
        List<String> tags = tagService.queryAllTags();
        // 返回所有不重复的标签列表
        return Result.succ(tags);
    }

    @PostMapping("/blog/tags/add")
    public Result addTag(@RequestParam String tagName) {
        // 执行添加标签操作
        tagService.addTag(tagName);
        // 返回添加成功
        return Result.succ("添加标签成功");
    }

    @PostMapping("/blog/tags/delete")
    public Result deleteTagByName(@RequestParam String tagName) {
        // 执行删除标签操作
        tagService.deleteTag(tagName);
        // 返回删除成功
        return Result.succ("删除标签成功");
    }
}
