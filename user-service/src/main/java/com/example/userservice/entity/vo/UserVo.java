package com.example.userservice.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {

    private Long id;

    private String username;

    private String email;

    private String avatar;

    private Integer status;

    private Date created;
}
