package com.example.blogservice.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

    private Long id;

    @NotBlank(message = "评论内容不能为空")
    private String content;

    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    private String username;


    @NotNull(message = "博客id不能为空")
    private Long blogId;

    private Long rootCommentId;

    private Long toCommentId;

}
