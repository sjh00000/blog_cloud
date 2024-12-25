package com.example.userservice.controller;


import com.example.commonservice.entity.Result;
import com.example.userservice.entity.dto.UserDto;
import com.example.userservice.entity.vo.UserVo;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sjh
 * @since 2024-05-16
 */
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取用户信息接口。
     * 该接口需要用户认证才能访问
     */
    @GetMapping("/user/info")
    public Result getUserInformation(@RequestParam String username) {
        UserVo userVo = userService.getUserInformation(username);
        // 返回操作成功的结果，并附带用户信息
        return Result.succ(userVo);
    }

    @PostMapping("/user/update")
    public Result updateUserInformation(@RequestBody UserDto userDto) {
        userService.updateUserInformation(userDto);
        return Result.succ("更新成功");
    }


}
