package com.example.searchservice.service;

import com.example.apiservice.entity.dao.ESblog;
import com.example.searchservice.repo.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lsym005169
 */
@Service
@Slf4j
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;


    public ESblog save(ESblog blog) {
        // 保存博客到 Elasticsearch
        return blogRepository.save(blog);
    }


    public void delete(Long id) {
        // 删除博客
        blogRepository.deleteById(id.toString());
    }

    public List<ESblog> searchByContentOrDescription(String keyword) {
//        log.info("{}",blogRepository.findAll());
        return blogRepository.findByContentOrDescription(keyword, keyword);
    }
}
