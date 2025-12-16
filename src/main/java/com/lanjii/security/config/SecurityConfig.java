package com.lanjii.security.config;

import com.lanjii.core.resp.R;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.security.UserDetailsServiceImpl;
import com.lanjii.security.filter.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * 安全配置
 *
 * @author lanjii
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    private List<String> whitelist;

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
                                        R.write(ResultCode.UNAUTHORIZED);
                                    } else {
                                        R.write(ResultCode.INTERNAL_SERVER_ERROR, ex.getMessage());
                                    }
                                }
                        )
                        .accessDeniedHandler((req, res, ex) -> {
                                    R.write(ResultCode.FORBIDDEN);
                                }
                        )
                )

                // 会话管理（无状态）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(whitelist.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )

                // 配置 iframe 和 CSP 策略
                .headers(headers -> headers
                        // 允许同源 iframe 嵌入
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                        // 允许前端域名嵌入（通过代理访问时是同源，直接访问后端时允许前端域名）
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("frame-ancestors 'self' http://127.0.0.1:1001 http://localhost:1001")
                        )
                )

                // 添加JWT过滤器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 关键修复点：暴露AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // 配置自定义的UserDetailsService
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Getter for whitelist
    public List<String> getWhitelist() {
        return whitelist;
    }

    // Setter for whitelist
    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // 允许凭证

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}