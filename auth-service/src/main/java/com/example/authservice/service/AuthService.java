package com.example.authservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authservice.entity.dao.UserDao;
import com.example.authservice.entity.dto.LoginDto;
import com.example.authservice.entity.dto.RegisterDto;
import com.example.authservice.entity.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sjh
 * @since 2024-05-16
 */
public interface AuthService extends IService<UserDao> {

    //注册新用户
    Integer registerUser(RegisterDto registerDto);

    //登录用户
    UserVo loginByUserName(LoginDto loginDto);

}
