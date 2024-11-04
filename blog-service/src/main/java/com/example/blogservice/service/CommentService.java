package com.example.blogservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogservice.entity.dao.CommentDao;
import com.example.blogservice.entity.dto.CommentDto;
import com.example.blogservice.entity.vo.CommentVo;

import java.util.List;

public interface CommentService extends IService<CommentDao> {
    List<CommentVo> getCommentList(Long blogId);

    void editComment(CommentDto commentDto);

    void removeCommentById(Long commentId);

    List<CommentVo> getReplyCommentList(Long commentId);
}
