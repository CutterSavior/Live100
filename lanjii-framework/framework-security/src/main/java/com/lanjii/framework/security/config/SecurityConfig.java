package com.lanjii.framework.security.config;

import com.lanjii.common.response.R;
import com.lanjii.common.response.ResultCode;
import com.lanjii.common.util.JacksonUtils;
import com.lanjii.framework.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

/**
 * 统一安全配置
 *
 * @author lanjii
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProperties securityProperties;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF（使用JWT不需要）
                .csrf(AbstractHttpConfigurer::disable)

                // 启用CORS配置
                .cors(Customizer.withDefaults())

                // 异常处理配置
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((req, res, ex) -> {
                            if (ex instanceof InsufficientAuthenticationException) {
                                writeJson(req, res, ResultCode.UNAUTHORIZED, null);
                            } else {
                                writeJson(req, res, ResultCode.INTERNAL_SERVER_ERROR, ex.getMessage());
                            }
                        })
                        .accessDeniedHandler((req, res, ex) -> {
                            writeJson(req, res, ResultCode.FORBIDDEN, null);
                        })
                )

                // 会话管理（无状态）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(securityProperties.getWhitelist().toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )

                // 配置 iframe 和 CSP 策略
                .headers(headers -> headers
                        // 允许同源 iframe 嵌入
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                        // 允许前端域名嵌入（通过代理访问时是同源，直接访问后端时允许前端域名）
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("frame-ancestors 'self' http://127.0.0.1:1001 http://localhost:1001 https://live100.vercel.app")
                        )
                )

                // 添加JWT过滤器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(
                "http://127.0.0.1:1001",
                "http://localhost:1001",
                "https://live100.vercel.app"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // 允许凭证

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 写入 JSON 响应
     */
    private void writeJson(HttpServletRequest request, HttpServletResponse response, ResultCode resultCode, String errMsg) throws IOException {
        String origin = request.getHeader("Origin");
        if (origin != null && !origin.isBlank()) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String msg = errMsg != null ? errMsg : resultCode.getMsg();
        response.getWriter().println(JacksonUtils.toJson(R.fail(resultCode.getCode(), msg)));
        response.getWriter().flush();
    }
}
