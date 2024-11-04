package com.example.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogservice.entity.dao.CommentDao;
import com.example.blogservice.entity.dto.CommentDto;
import com.example.blogservice.entity.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<CommentDao> {
    List<CommentDao> getCommentList(@Param("blogId") Long blogId);

    void editComment(@Param("commentDto")CommentDto commentDto);

    void removeCommentById(Long commentId);

    List<CommentDao> getReplyCommentList(Long commentId);
}
