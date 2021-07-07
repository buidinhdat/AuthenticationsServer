package com.mobifone.crm.authentication.Configuration.Service;

import com.mobifone.crm.authentication.Entity.UserEntity;
import com.mobifone.crm.authentication.Model.AuthModel.TokenData;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {
    TokenData generateTokenData(String userId);

    TokenData doRefreshToken(String refreshToken) throws Exception;

    Integer getJwtExpiration();

    Integer getJwtRefreshExpiration();

    boolean isValidRefreshToken(String refreshToken);

    public String getToken(HttpServletRequest request);

    TokenData generatesTokenDataByRefreshToken(UserEntity userEntity, String refreshToken);

}
