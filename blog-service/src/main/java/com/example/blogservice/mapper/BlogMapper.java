package com.example.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogservice.entity.dao.BlogDao;
import com.example.blogservice.entity.vo.BlogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sjh
 * @since 2024-05-16
 */
public interface BlogMapper extends BaseMapper<BlogDao> {

    IPage<BlogDao> getBlogList(@Param("page") Page<BlogVo> page);

    BlogDao getBlogById(@Param("blogId") Long blogId);

    void saveBlog(@Param("blogDao") BlogDao blogDao);

    void updateBlog(@Param("blogDao") BlogDao blogDao);

    IPage<BlogDao> searchBlogsWithList(@Param("page") Page<BlogVo> page, @Param("blogList")List<BlogDao> blogDao);

    IPage<BlogDao> queryByTag(@Param("page") Page<BlogVo> page, @Param("tag") String tag);

    void deleteBlog(@Param("blogId")Long blogId);

    List<BlogDao> getAll();
}
