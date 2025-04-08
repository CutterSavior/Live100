package com.lanjii.security;

import com.lanjii.config.support.WhiteListProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全认证配置
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(WhiteListProperties.class)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final WhiteListProperties whiteListProperties;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        List<String> whiteList = whiteListProperties.getUrls();
        http
                // 禁用basic明文验证
                .httpBasic().disable()
                // 基于 token，前后端分离架构不需要csrf保护
                .csrf().disable()
                // 禁用默认登录页
                .formLogin().disable()
                // 禁用默认登出页
                .logout().disable()
                .exceptionHandling()
                // 设置异常的EntryPoint，如果不设置，默认使用Http403ForbiddenEntryPoint
                .authenticationEntryPoint(authenticationEntryPoint)
                // 设置无权限访问的处理
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                // 前后端分离是无状态的，不需要session了，直接禁用。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 动态权限在新版本中只需要在这里处理即可
                .authorizeHttpRequests(registry -> registry
                        // 允许所有OPTIONS请求，用于前后端分离预检测调用（跨域请求）
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers(whiteList.toArray(new String[0])).permitAll()
                        .anyRequest().access((authentication, requestAuthorizationContext) -> {
                            // 登录后才可以访问
                            if (authentication.get() instanceof AnonymousAuthenticationToken) {
                                return new AuthorizationDecision(false);
                            }
                            AuthUser principal = (AuthUser) authentication.get().getPrincipal();
                            if (principal.getSysUser().getIsAdmin() == 1) {
                                return new AuthorizationDecision(true);
                            }

                            // 获取当前请求的 URL 地址
                            String requestURI = requestAuthorizationContext.getRequest().getRequestURI();
                            // 确保路径格式一致（去掉重复的斜杠等）
                            String normalizedPath = StringUtils.cleanPath(requestURI);
                            // 获取用户所有权限地址，这里的权限地址是 loadUserByUsername 添加的。
                            List<String> perUrls = authentication.get().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                            boolean hasPermission = perUrls.stream()
                                    .anyMatch(pattern -> pathMatcher.match(pattern, normalizedPath));
                            if (!hasPermission) {
                                log.warn("Access denied to {} - no matching permission patterns found", requestURI);
                            }
                            return new AuthorizationDecision(hasPermission);
                        }))
                // 使用 JWT 过滤器，替代UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // iframe 嵌套页面要使用
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
