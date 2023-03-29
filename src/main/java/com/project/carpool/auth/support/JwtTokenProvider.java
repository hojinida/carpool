package com.project.carpool.auth.support;

import com.project.carpool.auth.domain.RefreshToken;
import com.project.carpool.auth.domain.repository.RefreshTokenRepository;
import com.project.carpool.user.domain.User;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

        @Value("${jwt.secret}")
        private String secretKey;

        @Value("${jwt.response.header}")
        private String jwtHeader;

        @Value("${jwt.token.prefix}")
        private String jwtTokenPrefix;

        private final long accessTokenValidTime = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
        private final long refreshTokenValidTime = Duration.ofDays(14).toMillis(); // 만료시간 2주

        private final UserDetailsService userDetailsService;
        private final RefreshTokenRepository refreshTokenRepository;

        @PostConstruct
        protected void init() {
            secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        }

        public String createAccessToken(User user) {
            Claims claims = Jwts.claims().setSubject(user.getUsername());
            Date now = new Date();

            return Jwts.builder()
                    .setClaims(claims) // 정보
                    .setIssuedAt(now) // 토큰 발행 시간
                    .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 토큰 만료 시간
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        }

        public RefreshToken createRefreshToken(User user) {
            Claims claims = Jwts.claims().setSubject(UUID.randomUUID().toString());
            Date now = new Date();

            return RefreshToken.builder()
                    .key(claims.getSubject())
                    .value(Jwts.builder()
                            .setClaims(claims) // 정보
                            .setIssuedAt(now) // 토큰 발행 시간
                            .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // 토큰 만료 시간
                            .signWith(SignatureAlgorithm.HS256, secretKey)
                            .compact())
                    .expiredTime(refreshTokenValidTime)
                    .build();
        }

        public Authentication getAuthentication(String token) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }

        public String getUsername(String token) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        }

        public String resolveToken(HttpServletRequest request) {
            String bearerToken = request.getHeader(jwtHeader);
            if (StringUtils.hasText
}
