package com.example.blogservice.feign;

import com.example.blogservice.entity.dao.BlogDao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "search-service", url = "http://localhost:8084/es/blogs")
public interface ElasticsearchFeignClient {

    @PostMapping("/save")
    BlogDao save(@RequestBody BlogDao blogDao);

    @PostMapping("/search")
    List<BlogDao> searchByKeyWord(@RequestParam("keyword") String keyword);

    @PostMapping("/delete/{id}")
    void delete(@PathVariable("id") Long id);
}

