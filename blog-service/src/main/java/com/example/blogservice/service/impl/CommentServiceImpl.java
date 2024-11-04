package com.example.blogservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogservice.entity.dao.CommentDao;
import com.example.blogservice.entity.dto.CommentDto;
import com.example.blogservice.entity.vo.CommentVo;
import com.example.blogservice.mapper.CommentMapper;
import com.example.blogservice.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDao> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentVo> getCommentList(Long blogId) {
        List<CommentDao> commentDaoList =  commentMapper.getCommentList(blogId);
        List<CommentVo> commentVoList=new ArrayList<>();
        commentDaoList.forEach(commentDao -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(commentDao, commentVo);
            commentVoList.add(commentVo);
        });
        return commentVoList;
    }

    @Override
    public void editComment(CommentDto commentDto) {
        commentMapper.editComment(commentDto);
    }

    @Override
    public void removeCommentById(Long commentId) {
        commentMapper.removeCommentById(commentId);
    }

    @Override
    public List<CommentVo> getReplyCommentList(Long commentId) {
        List<CommentDao> replyCommentList =  commentMapper.getReplyCommentList(commentId);
        List<CommentVo> replyCommentVoList=new ArrayList<>();
        replyCommentList.forEach(commentDao -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(commentDao, commentVo);
            replyCommentVoList.add(commentVo);
        });
        return replyCommentVoList;
    }
}
