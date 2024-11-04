package com.example.searchservice.service;

import com.example.searchservice.entity.Blog;
import com.example.searchservice.repo.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;


    public Blog save(Blog blog) {
        return blogRepository.save(blog);  // 保存博客到 Elasticsearch
    }


    public void delete(Long id) {
        blogRepository.deleteById(id.toString());  // 删除博客
    }

    public List<Blog> searchByContentOrDescription(String keyword) {
//        log.info("{}",blogRepository.findAll());
        return blogRepository.findByContentOrDescription(keyword, keyword);
    }
}
