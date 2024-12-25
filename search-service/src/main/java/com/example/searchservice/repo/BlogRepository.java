package com.example.searchservice.repo;

import com.example.apiservice.entity.dao.ESblog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lsym005169
 */
@Repository
public interface BlogRepository extends ElasticsearchRepository<ESblog, String> {
    List<ESblog> findByContentOrDescription(String content, String description);
}