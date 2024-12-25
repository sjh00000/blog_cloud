package com.example.blogservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.apiservice.entity.dao.ESblog;
import com.example.apiservice.feign.ElasticsearchFeignClient;
import com.example.blogservice.entity.dao.BlogDao;
import com.example.blogservice.entity.dto.BlogDto;
import com.example.blogservice.entity.vo.BlogVo;
import com.example.blogservice.mapper.BlogMapper;
import com.example.blogservice.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sjh
 * @since 2024-05-16
 */
@Service
@Slf4j
public class BlogServiceImpl extends ServiceImpl<BlogMapper, BlogDao> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ElasticsearchFeignClient elasticsearchFeignClient;

    private final String cacheBlogInfoKey = "blog:info";


    @Override
    public IPage<BlogVo> getBlogList(Page<BlogVo> page) {
        IPage<BlogDao> blogDaoIPage = blogMapper.getBlogList(page);
        IPage<BlogVo> blogVoIPage = new Page<>();
        BeanUtils.copyProperties(blogDaoIPage, blogVoIPage);

        log.info("当前记录为{}", blogDaoIPage.getRecords());
        return blogVoIPage;
    }

    @Override
    public BlogVo getBlogById(Long blogId) {
        // 尝试从 Redis 缓存中获取博客信息
        BlogDao cachedBlog = (BlogDao) redisTemplate.opsForHash().get(cacheBlogInfoKey, blogId.toString());

        if (cachedBlog != null) {
            // 如果缓存中有数据，则直接返回转换后的 BlogVo
            log.info("Redis找到博客数据");
            BlogVo blogVo = new BlogVo();
            BeanUtils.copyProperties(cachedBlog, blogVo);
            return blogVo;
        } else {
            // 如果缓存中没有数据，则从数据库查询数据
            log.info("Redis未找到博客数据");
            BlogDao blogDao = blogMapper.getBlogById(blogId);
            BlogVo blogVo = new BlogVo();
            BeanUtils.copyProperties(blogDao, blogVo);

            // 将查询结果保存到缓存中
            saveBlogInfoToCache(blogDao);

            return blogVo;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editBlogDetail(BlogDto blogDto) {
        BlogDao blogDao;
        // 根据博客ID判断是更新现有博客还是创建新博客
        if (blogDto.getId() != null) {
            blogDao = (BlogDao) redisTemplate.opsForHash().get(cacheBlogInfoKey, blogDto.getId().toString());
            if (blogDao == null) {
                log.info("Redis未找到对应的博客");
                blogDao = blogMapper.getBlogById(blogDto.getId());
            } else {
                log.info("Redis中找到对应的博客");
                //先删除缓存
                Assert.isTrue(Objects.equals(blogDao.getUserId(), blogDto.getUserId()), "没有权限编辑");
                deleteBlogFromCache(blogDao.getId());
            }
            //此时为null说明数据库里也没有该博客
            if (blogDao != null) {
                log.info("博客已存在，作者是：{}", blogDao.getUserId());
                Assert.isTrue(Objects.equals(blogDao.getUserId(), blogDto.getUserId()), "没有权限编辑");
                BeanUtil.copyProperties(blogDto, blogDao, "id", "userId", "username", "created");
                //修改数据库
                blogMapper.updateBlog(blogDao);
                ESblog blogDaoForEs = new ESblog();
                BeanUtils.copyProperties(blogDao, blogDaoForEs);
                //修改es(如果es修改失败那么数据库会进行回滚)
//                elasticsearchFeignClient.save(blogDaoForEs);
                return true;
            } else {
                log.info("未找到对应id的博客");
                return false;
            }
        } else {
            blogDao = new BlogDao();
            blogDao.setUserId(blogDto.getUserId());
            blogDao.setUsername(blogDto.getUsername());
            blogDao.setCreated(Date.from(Instant.now()));
            log.info("新增博客");
            // 复制博客属性到临时对象，忽略特定属性以防止覆盖
            BeanUtil.copyProperties(blogDto, blogDao, "id", "userId", "username", "created");
            //先保存到数据库再修改缓存
            blogMapper.saveBlog(blogDao);
            //添加到es
            ESblog blogDaoForEs = new ESblog();
            BeanUtils.copyProperties(blogDao, blogDaoForEs);
//            elasticsearchFeignClient.save(blogDaoForEs);
//            pushBlogToRedis(blogDao);
            return true;
        }

    }


    @Override
    public IPage<BlogVo> searchBlogsLikeTitleOrContent(Page<BlogVo> page, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            IPage<BlogDao> blogDaoPage =blogMapper.getBlogList(page);
            IPage<BlogVo> blogVoPage = new Page<>();
            BeanUtils.copyProperties(blogDaoPage, blogVoPage);
            return blogVoPage;
        }
        IPage<BlogDao> blogDaoIPage = new Page<>();
//        //TODO es服务查询匹配数据
//        List<ESblog> blogApiList = elasticsearchFeignClient.searchByKeyWord(keyword);
//        List<com.example.blogservice.entity.dao.BlogDao> blogList = blogApiList.stream()
//                .map(apiBlog -> {
//                    com.example.blogservice.entity.dao.BlogDao serviceBlog = new com.example.blogservice.entity.dao.BlogDao();
//                    serviceBlog.setId(apiBlog.getId());
//                    serviceBlog.setTitle(apiBlog.getTitle());
//                    serviceBlog.setContent(apiBlog.getContent());
//                    // 设置其他属性
//                    return serviceBlog;
//                })
//                .collect(Collectors.toList());
//        log.info("从es中查询到数据为：{}", blogList);
//        if(!blogList.isEmpty()){
//            blogDaoIPage = blogMapper.searchBlogsWithList(page, blogList);
//        }
        blogDaoIPage = blogMapper.searchBlogsWithKey(page, keyword);
        IPage<BlogVo> blogVoIPage = new Page<>();
        BeanUtils.copyProperties(blogDaoIPage, blogVoIPage);
        return blogVoIPage;
    }

    @Override
    public IPage<BlogVo> queryByTag(Page<BlogVo> page, String tag) {
        IPage<BlogDao> blogDaoIPage = blogMapper.queryByTag(page, tag);
        IPage<BlogVo> blogVoIPage = new Page<>();
        BeanUtils.copyProperties(blogDaoIPage, blogVoIPage);
        return blogVoIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlog(Long blogId) {
        // 尝试从 Redis 缓存中获取博客信息
        BlogDao cachedBlog = (BlogDao) redisTemplate.opsForHash().get(cacheBlogInfoKey, blogId.toString());
        if (cachedBlog != null) {
            //删除缓存
            deleteBlogFromCache(blogId);
        }
        //删除数据库
        blogMapper.deleteBlog(blogId);
        //删除es中的blog
        elasticsearchFeignClient.delete(blogId);
    }


    private void saveBlogInfoToCache(BlogDao blogDao) {
        redisTemplate.opsForHash().put(cacheBlogInfoKey, blogDao.getId().toString(), blogDao);
    }


    private void deleteBlogFromCache(Long blogId) {
        redisTemplate.opsForHash().delete(cacheBlogInfoKey, blogId.toString());
    }
}
