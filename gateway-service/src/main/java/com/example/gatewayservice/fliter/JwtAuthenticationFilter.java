package com.example.gatewayservice.fliter;

import com.example.gatewayservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        log.info("请求路径："+request.getURI());

        //如果是如下请求则放行
        List<String> allowedPaths = Arrays.asList("/account/login","/account/register", "/user/info","/blog/list","/blog/detail",
                "/blog/search","/blog/queryByTag","/blog/comment","/es/data","/blog/tags");

        if (allowedPaths.stream().anyMatch(path -> request.getURI().getPath().contains(path))) {
            return chain.filter(exchange);
        }

        log.info("未通过");
        HttpHeaders headers = request.getHeaders();
        String accessToken = headers.getFirst("Authorization_Access");
        String refreshToken = headers.getFirst("Authorization_Refresh");

        log.info("accessToken:"+accessToken+" refreshToken:"+refreshToken);
        //检验accessToken和refreshToken
        if (accessToken != null && !accessToken.isEmpty()) {
            String flagAccessToken = "1";
            Claims accessClaim = jwtUtils.getClaimByToken(accessToken, flagAccessToken);
            //AccessToken 有效
            if(accessClaim != null && !jwtUtils.isTokenExpired(accessClaim.getExpiration())){
                return chain.filter(exchange);
            }else{
                //AccessToken 无效 401,header加上DoRefresh
                log.info("accessToken无效{}",accessClaim);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("DoRefresh", "1");
                return response.setComplete();
            }
        }else if(refreshToken != null && !refreshToken.isEmpty()){
            String flagRefreshToken = "2";
            Claims refreshClaim = jwtUtils.getClaimByToken(refreshToken, flagRefreshToken);
            if(refreshClaim != null && !jwtUtils.isTokenExpired(refreshClaim.getExpiration())){
                //refreshToken有效，则生成新的accessToken
                long userId = Long.parseLong(refreshClaim.getSubject());
                String newAccessToken = jwtUtils.generateAccessToken(userId);
                response.getHeaders().add("Authorization_Access", newAccessToken);
                return chain.filter(exchange);
            }else{
                //RefreshToken 无效 401
                log.info("accessToken无效{}" ,refreshClaim);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }
        //两个token均为空 401
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
