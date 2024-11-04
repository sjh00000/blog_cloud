package com.example.blogservice.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BlogDto {
    private Long id;

    //登录的用户id
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "博客标题不能为空")
    private String title;

    private String description;

    @NotBlank(message = "博客内容不能为空")
    private String content;

    private Date created;

    private String tag;
}
