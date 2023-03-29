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

        String token = Jwts.builder()
                .setClaims(claims) // 정보
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() +refreshTokenValidTime)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return RefreshToken.builder()
                .key(user.getUsername())
                .value(token)
                .expiredTime(refreshTokenValidTime)
                .build();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    // 토큰으로 회원 정보 조회
    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // GET ACCESS TOKEN BY HEADER
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtTokenPrefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("refreshToken");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtTokenPrefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Date getExpiration(String accessToken){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getExpiration();
    }

    // 토큰의 유효 및 만료 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
            return false;
        } catch(UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            return false;
        } catch(IllegalArgumentException e) {
            log.error("JWT token is invalid");
            return false;
        } catch (ExpiredJwtException e){
            log.error("JWT token is expired");
            return false;
        }
    }
}
