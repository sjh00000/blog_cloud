package com.example.searchservice.controller;

import com.example.searchservice.entity.Blog;
import com.example.searchservice.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/es/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/save")
    public Blog save(@RequestBody Blog blog) {
        return blogService.save(blog);
    }

    @PostMapping("/search")
    public List<Blog> searchByKeyWord(@RequestParam String keyword) {
        log.info("keyword:{}", keyword);
        return blogService.searchByContentOrDescription(keyword);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        blogService.delete(id);
    }
}
