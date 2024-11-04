package com.example.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userservice.entity.dao.UserDao;
import com.example.userservice.entity.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    UserDao getUserByUserName(@Param("username")String userName);

    UserDao getUserByUserId(@Param("userId")Long userId);

    void updateUserInformation(@Param("userDto")UserDto userDto);
}
