package com.example.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.userservice.entity.dao.UserDao;
import com.example.userservice.entity.dto.UserDto;
import com.example.userservice.entity.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sjh
 * @since 2024-05-16
 */
public interface UserService extends IService<UserDao> {


    UserVo getUserInformation(String username);

    void updateUserInformation(UserDto userDto);
}
