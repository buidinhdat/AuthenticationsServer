package com.mobifone.crm.authentication.Configuration.Service.Impl;

import com.mobifone.crm.authentication.Configuration.Service.JwtTokenService;
import com.mobifone.crm.authentication.Configuration.TokenJwtUtil;
import com.mobifone.crm.authentication.Entity.UserEntity;
import com.mobifone.crm.authentication.Model.AuthModel.TokenData;
import com.mobifone.crm.authentication.Service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    @Autowired
    public TokenJwtUtil tokenJwtUtil;

    @Autowired
    public UserService userService;

    @Override
    public TokenData generateTokenData(String userId) {
        TokenData tokentData = new TokenData();
        tokentData.setAccessToken(tokenJwtUtil.generateAccessToken(userId));
        tokentData.setRefreshToken(tokenJwtUtil.generateRefreshToken(userId));
        Date accessTokenExpiredAt = tokenJwtUtil.getExpirationDateFromToken(tokentData.getAccessToken());
        Date refreshTokenExpiredAt = tokenJwtUtil.getExpirationDateFromRefreshToken(tokentData.getRefreshToken());
        tokentData.setAccessTokenExpiredAt(accessTokenExpiredAt);
        tokentData.setRefreshTokenExpiredAt(refreshTokenExpiredAt);
        return tokentData;

    }


    @Override
    public String getToken(HttpServletRequest request) {
        return tokenJwtUtil.getToken(request);
    }

    @Override
    public TokenData generatesTokenDataByRefreshToken(UserEntity userEntity, String refreshToken) {
        TokenData tokentData = new TokenData();
        tokentData.setAccessToken(tokenJwtUtil.generateAccessToken(String.valueOf(userEntity.getUserId())));
        tokentData.setRefreshToken(refreshToken);
        Date accessTokenExpiredAt = tokenJwtUtil.getExpirationDateFromToken(tokentData.getAccessToken());
        // because refresh token use only secret key to generate => call getClaimFromToken explict
        Date refreshTokenExpiredAt = tokenJwtUtil.getExpirationDateFromRefreshToken(refreshToken);
        tokentData.setAccessTokenExpiredAt(accessTokenExpiredAt);
        tokentData.setRefreshTokenExpiredAt(refreshTokenExpiredAt);
        return tokentData;
    }

    @Override
    public TokenData doRefreshToken(String refreshToken) throws Exception {
        TokenData tokenData = null;
        try {
            if(!tokenJwtUtil.isValidRefreshToken(refreshToken)){
                throw new Exception("Invalid token >>> Cannot parse secret-key token");
            }
            if(tokenJwtUtil.isRefreshTokenExpired(refreshToken)){
                throw new Exception("RefreshToken is expired");
            }
            String userId = tokenJwtUtil.getClaimFromRefreshToken(refreshToken, Claims::getSubject);
            UserEntity userDetails = null;
            if (userId != null) {
                userDetails = userService.getUserByUserId(Long.parseLong(userId));
            }

            if (userDetails != null) {
                tokenData = generatesTokenDataByRefreshToken(userDetails, refreshToken);

            } else {
                throw new Exception("RefreshToken is not allow");
            }
        } catch (Exception ex) {
            throw ex;
        }

        return tokenData;
    }

    @Override
    public Integer getJwtExpiration() {
        return null;
    }

    @Override
    public Integer getJwtRefreshExpiration() {
        return null;
    }

    @Override
    public boolean isValidRefreshToken(String refreshToken) {
        return false;
    }


}
