package com.example.blogservice.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BlogVo {
    private Long id;

    private String username;

    private String title;

    private String description;

    private String content;

    private Date created;

    private String tag;
}
