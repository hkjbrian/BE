package SJUCapstone.BE.auth.service;

import SJUCapstone.BE.auth.dto.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static java.lang.System.getenv;

@Service
public class AuthService {

    Map<String, String> env = getenv();
    private final String secretKey = env.get("JWT_SECRET_KEY");

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24; // 1일
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일

    // JWT 서명에 사용할 Key 객체
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    // 2개 Token 생성
    public TokenResponse generateTokens(String email) {
        String accessToken = generateAccessToken(email);
        String refreshToken = generateRefreshToken(email);

        return new TokenResponse(accessToken, refreshToken);
    }

    // Access Token 생성
    private String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 사용자 식별자
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 키 설정
                .compact();
    }

    // Refresh Token 생성
    private String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 사용자 식별자
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 키 설정
                .compact();
    }

    // JWT 토큰 해독 및 사용자 ID 추출
    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody();

        return claims.getSubject(); // 토큰의 subject에서 email을 추출
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 유효하지 않은 경우
            return false;
        }
    }
}
