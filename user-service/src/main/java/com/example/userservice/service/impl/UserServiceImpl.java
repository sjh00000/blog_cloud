package com.example.userservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userservice.entity.dao.UserDao;
import com.example.userservice.entity.dto.UserDto;
import com.example.userservice.entity.vo.UserVo;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDao> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String cacheUserInfoKey = "user:info";

    @Override
    public UserVo getUserInformation(String username) {
        UserDao userDao = getUserFromRedis(username);
        if (userDao == null) {
            log.info("Redis中未找到目标用户");
            userDao = userMapper.getUserByUserName(username);
            saveUserInfoToRedis(userDao);
            log.info("111");
            redisTemplate.expire(cacheUserInfoKey, 2, TimeUnit.DAYS);
        } else {
            log.info("Redis中找到目标用户{}", userDao);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDao, userVo);

        return userVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInformation(UserDto userDto) {
        UserDao userDao = getUserFromRedis(userDto.getId());
        if(userDao==null){
            log.info("Redis中未找到目标用户");
        }else{
            //有缓存先删除缓存
            log.info("Redis中找到目标用户");
            deleteUserInfoFromRedis(userDao);
        }
        userMapper.updateUserInformation(userDto);
    }

    private UserDao getUserFromRedis(String username) {
        return (UserDao) redisTemplate.opsForHash().get(cacheUserInfoKey, username);
    }

    private UserDao getUserFromRedis(Long userId) {
        return (UserDao) redisTemplate.opsForHash().get(cacheUserInfoKey, userId.toString());
    }

    private void saveUserInfoToRedis(UserDao userDao) {
        redisTemplate.opsForHash().put(cacheUserInfoKey, userDao.getUsername(), userDao);
        redisTemplate.opsForHash().put(cacheUserInfoKey, userDao.getId().toString(), userDao);
    }

    private void deleteUserInfoFromRedis(UserDao userDao) {
        redisTemplate.opsForHash().delete(cacheUserInfoKey, userDao.getUsername());
        redisTemplate.opsForHash().delete(cacheUserInfoKey, userDao.getId().toString());
    }

}
