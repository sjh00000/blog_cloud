package com.example.apiservice.feign;

import com.example.apiservice.entity.dao.ESblog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lsym005169
 */
@FeignClient(name = "search-service/es/blogs")
public interface ElasticsearchFeignClient {

    @PostMapping("/save")
    ESblog save(@RequestBody ESblog eSblog);

    @PostMapping("/search")
    List<ESblog> searchByKeyWord(@RequestParam("keyword") String keyword);

    @PostMapping("/delete/{id}")
    void delete(@PathVariable("id") Long id);
}

