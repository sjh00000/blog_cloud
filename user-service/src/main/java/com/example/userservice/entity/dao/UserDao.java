package com.example.userservice.entity.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sjh
 * @since 2024-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("users")
public class UserDao implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户id
    private Long id;

    //昵称
    @NotBlank(message = "昵称不能为空")
    private String username;

    //密码
    private String password;

    //头像
    private String avatar;

    //邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    //状态
    private Integer status;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date created;


}