package com.example.authservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.authservice.entity.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sjh
 * @since 2024-05-16
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDao> {
    void registerUser(@Param("userDao")UserDao userDao);

    List<Integer> checkUserExist(@Param("username")String username, @Param("email")String email);

    UserDao getUserByUserName(@Param("username")String userName);

    UserDao getUserByUserId(@Param("userId")Long userId);
}
