package com.example.blogservice.controller;

import com.example.blogservice.entity.dto.CommentDto;
import com.example.blogservice.entity.vo.CommentVo;
import com.example.blogservice.resp.Result;
import com.example.blogservice.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 查询评论列表
     */
    @GetMapping("/blog/comment")
    public Result getCommentList(@RequestParam Long blogId) {
        List<CommentVo> commentList = commentService.getCommentList(blogId);
        return Result.succ(commentList);
    }

    @GetMapping("/blog/comment/getReply")
    public Result getReplyCommentList(@RequestParam Long commentId) {
        List<CommentVo> replyList = commentService.getReplyCommentList(commentId);
        return Result.succ(replyList);
    }

    /**
     * 添加评论
     */
    @PostMapping("/blog/comment/add")
    public Result editComment(@RequestBody CommentDto commentDto) {
        // 执行分页查询，并返回查询结果
        commentService.editComment(commentDto);
        return Result.succ("评论成功");
    }

    @PostMapping("/blog/comment/delete")
    public Result deleteComment(@RequestParam Long commentId) {
        commentService.removeCommentById(commentId);
        return Result.succ("已删除评论");
    }


}
