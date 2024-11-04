package com.example.searchservice.repo;

import com.example.searchservice.entity.Blog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends ElasticsearchRepository<Blog, String> {
    List<Blog> findByContentOrDescription(String content, String description);
}