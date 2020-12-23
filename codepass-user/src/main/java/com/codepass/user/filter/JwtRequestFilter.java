package com.codepass.user.filter;

import com.codepass.user.config.JwtTokenUtil;
import com.codepass.user.service.JwtUserDetailsService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器 用于 Spring Boot Security
 * OncePerRequestFilter 一次请求只通过一次filter，而不需要重复执行
 * JwtRequestFilter继承了Spring Web的OncePerRequestFilter类。
 * 对于任何传入请求，都会执行此Filter类。它检查请求是否具有有效的JWT令牌。
 * 如果它具有有效的JWT令牌，则它将在上下文中设置Authentication，以指定当前用户已通过身份验证。
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        // JWT Token 获取请求头部的 Bearer
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = this.jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (JwtException e) {
                System.out.println("Unable to get JWT Token");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // 验证
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

            // 每个用户最多允许一个token有效
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)
                    && jwtToken.equals(stringRedisTemplate.opsForValue().get(username))) {
                // JWT 验证通过 使用Spring Security 管理
                UsernamePasswordAuthenticationToken usPaAuToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usPaAuToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usPaAuToken);
            }
        }
        chain.doFilter(request, response);
    }

}