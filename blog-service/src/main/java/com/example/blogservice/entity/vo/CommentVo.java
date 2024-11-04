package com.example.blogservice.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {

    private Long id;

    private String content;

    private Long userId;

    private String username;

    private Long blogId;

    private Long rootCommentId;

    private Long toCommentId;

    private Date created;
}
