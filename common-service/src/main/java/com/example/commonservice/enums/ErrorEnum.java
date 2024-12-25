package com.example.commonservice.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author lsym005169
 * @date 2024/12/07
 */
@Getter
public enum ErrorEnum {
    AUTHORIZATION_FAILED(60001, "鉴权失败"),
    UNAUTHORIZED(60002, "实体校验失败"),
    FORBIDDEN(60003, "禁止访问"),
    NOT_FOUND(60004, "未找到资源"),
    METHOD_NOT_ALLOWED(60005, "方法不允许"),
    NOT_ACCEPTABLE(60006, "请求的资源不可接受"),
    REQUEST_TIMEOUT(60007, "请求超时"),
    CONFLICT(60008, "资源冲突"),
    GONE(60009, "资源已过期"),
    VALIDATION_FAILED(60010, "参数校验失败"),


    ;

    private ErrorEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
    private final Integer code;
    private final String msg;
}
