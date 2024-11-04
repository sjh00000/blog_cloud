package com.example.userservice.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    //用户id
    @NotBlank(message = "用户唯一标识id不能为空")
    private Long id;
    //昵称
    @NotBlank(message = "昵称不能为空")
    private String username;
    //头像
    private String avatar;
    //邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

}
