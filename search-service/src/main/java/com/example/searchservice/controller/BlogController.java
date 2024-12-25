package com.example.searchservice.controller;

import com.example.apiservice.entity.dao.ESblog;
import com.example.apiservice.feign.ElasticsearchFeignClient;
import com.example.searchservice.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lsym005169
 */
@Slf4j
@RestController
@RequestMapping("/es/blogs")
public class BlogController implements ElasticsearchFeignClient{

    @Autowired
    private BlogService blogService;

    @Override
    @PostMapping("/save")
    public ESblog save(@RequestBody ESblog blog) {
        return blogService.save(blog);
    }

    @Override
    @PostMapping("/search")

    public List<ESblog> searchByKeyWord(@RequestParam String keyword) {
        log.info("keyword:{}", keyword);
        return blogService.searchByContentOrDescription(keyword);
    }

    @Override
    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        blogService.delete(id);
    }
}
