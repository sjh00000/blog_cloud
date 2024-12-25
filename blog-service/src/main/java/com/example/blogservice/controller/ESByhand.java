package com.example.blogservice.controller;

import com.example.apiservice.entity.dao.ESblog;
import com.example.apiservice.feign.ElasticsearchFeignClient;
import com.example.blogservice.entity.dao.BlogDao;
import com.example.blogservice.mapper.BlogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ESByhand {
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    ElasticsearchFeignClient elasticsearchFeignClient;
    @PostMapping("/es/data")
    public void addData(){
        List<BlogDao> blogDaoList = blogMapper.getAll();
        blogDaoList.forEach(blogDao -> {
            ESblog blogDaoForEs = new ESblog();
            BeanUtils.copyProperties(blogDao, blogDaoForEs);
            elasticsearchFeignClient.save(blogDaoForEs);
        });
    }

}
