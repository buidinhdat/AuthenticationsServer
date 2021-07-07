package com.mobifone.crm.authentication.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.function.Function;

@Component
public class TokenJwtUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${SECRET_KEY_CREATE_REFRESH_TOKEN}")
    private String SECRET_KEY_CREATE_REFRESH_TOKEN;

    @Value("${TOKEN_PREFIX}")
    private String TOKEN_PREFIX = "Bearer ";

    @Value("${HEADER_STRING}")
    private String HEADER_STRING = "Authorization";

    @Value("${ACCESS_TOKEN_EXPIRATION}")
    private String ACCESS_TOKEN_EXPIRATION;

    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private String REFRESH_TOKEN_EXPIRATION;

    //ACCESSTOKEN

    public String generateAccessToken(String userId) {

        String JWT = Jwts.builder().setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(ACCESS_TOKEN_EXPIRATION)))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return JWT;
    }

    public boolean isValidToken(String authToken, boolean withExpireTime) {
        return (withExpireTime && isValidToken(authToken) && !isTokenExpired(authToken))
                || (!withExpireTime && isValidToken(authToken));

    }


    public boolean isValidToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Invalid token >>> Cannot parse secret-key token - " + e.getMessage());
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //END ACCESSTOKEN


    //REFRESH TOKEN


    public String generateRefreshToken(String userId) {
        String JWT = Jwts.builder().setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(REFRESH_TOKEN_EXPIRATION)))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY_CREATE_REFRESH_TOKEN)
                .claim("userId", userId)
                .compact();
        return JWT;
    }


    public boolean isValidRefreshToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY_CREATE_REFRESH_TOKEN).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Invalid token >>> Cannot parse secret-key token - " + e.getMessage());
        }
        return false;
    }

    public boolean isValidRefreshToken(String authToken, boolean withExpireTime) {
        return (withExpireTime && isValidRefreshToken(authToken) && !isRefreshTokenExpired(authToken))
                || (!withExpireTime && isValidRefreshToken(authToken));

    }

    public boolean isRefreshTokenExpired(String token) {
        final Date expiration = getExpirationDateFromRefreshToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromRefreshToken(String token) {
        return getClaimFromRefreshToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromRefreshToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromRefreshToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromRefreshToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY_CREATE_REFRESH_TOKEN).parseClaimsJws(token).getBody();
    }
    //END REFRESHTOKEN




    public Long getUserIdFromJwtToken(String token) {
        Object userIdObj;
        userIdObj = getClaimFromToken(token, (c) -> c.get("sub"));
        return userIdObj != null ? Long.valueOf(userIdObj.toString()) : null;
    }


    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER_STRING);
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
