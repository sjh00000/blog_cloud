package com.example.authservice.controller;

import com.example.commonservice.entity.Result;
import com.example.authservice.entity.dto.LoginDto;
import com.example.authservice.entity.dto.RegisterDto;
import com.example.authservice.entity.vo.UserVo;
import com.example.authservice.service.AuthService;
import com.example.authservice.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
public class AccountController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 处理用户登录请求。
     * 通过验证用户名和密码，如果验证成功，返回包含用户信息和JWT令牌的结果。
     */
    @PostMapping("/account/login")
    public Result accountLogin(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        log.info("进入controller");
        // 根据用户名查询并验证用户
        UserVo userVo = authService.loginByUserName(loginDto);
        if (userVo == null) {
            return Result.fail("登录失败，请检查密码与账号是否正确");
        }
        // 生成JWT令牌，用于用户身份验证。
        String accessToken = jwtUtils.generateAccessToken(userVo.getId());
        String refreshToken = jwtUtils.generateRefreshToken(userVo.getId());
        log.info("生成的accessToken为：{}", accessToken);
        log.info("生成的refreshToken为：{}", refreshToken);
        // 设置HTTP响应头，将JWT令牌返回给客户端。
        response.setHeader("Authorization_Access", accessToken);
        response.setHeader("Authorization_Refresh", refreshToken);
        // 返回成功结果，包含用户基本信息。
        return Result.succ(userVo);
    }


    @PostMapping("/account/register")
    public Result accountRegister(@Valid @RequestBody RegisterDto registerDto) {
        // 调用UserService的注册方法
        int result = authService.registerUser(registerDto);
        if (result == 1) {
            return Result.fail("用户名已存在");

        } else if(result==2){
            return Result.fail("邮箱已存在");
        }else {
            return Result.succ(null);
        }
    }

}