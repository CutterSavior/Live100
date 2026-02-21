package com.lanjii.framework.security.util;

import com.lanjii.framework.security.config.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 工具类
 *
 * @author lanjii
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final SecurityProperties securityProperties;

    private String getSecret() {
        return securityProperties.getJwt().getSecret();
    }

    private Long getExpiration() {
        return securityProperties.getJwt().getExpiration();
    }

    public String generateToken(String username) {
        return generateToken(username, null, null);
    }

    public String generateToken(String username, Long tenantId, Long packageId) {
        var builder = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + getExpiration()));
        if (tenantId != null) {
            builder.claim("tenantId", tenantId);
        }
        if (packageId != null) {
            builder.claim("packageId", packageId);
        }
        return builder.signWith(Keys.hmacShaKeyFor(getSecret().getBytes())).compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Long getTenantIdFromToken(String token) {
        return getClaims(token).get("tenantId", Long.class);
    }

    public Long getPackageIdFromToken(String token) {
        return getClaims(token).get("packageId", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(getSecret().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(getSecret().getBytes()))
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token的过期时间
     *
     * @param token JWT token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(getSecret().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration();
    }

    /**
     * 检查token是否过期
     *
     * @param token JWT token
     * @return true-已过期，false-未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
