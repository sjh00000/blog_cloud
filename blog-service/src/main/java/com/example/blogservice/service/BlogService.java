package com.example.blogservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogservice.entity.dao.BlogDao;
import com.example.blogservice.entity.dto.BlogDto;
import com.example.blogservice.entity.dto.CommentDto;
import com.example.blogservice.entity.vo.BlogVo;
import com.example.blogservice.entity.vo.CommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sjh
 * @since 2024-05-16
 */
public interface BlogService extends IService<BlogDao> {

    IPage<BlogVo> getBlogList(Page<BlogVo> page);

    BlogVo getBlogById(Long blogId);

    Boolean editBlogDetail(BlogDto blogDto);

    IPage<BlogVo> searchBlogsLikeTitleOrContent(Page<BlogVo> page, String keyword);

    IPage<BlogVo> queryByTag(Page<BlogVo> page, String tag);

    void deleteBlog(Long blogId);
}
