package com.mobifone.crm.authentication.Model.AuthModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TokenData {
    private String accessToken;
    private String refreshToken;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date accessTokenExpiredAt;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date refreshTokenExpiredAt;

    public TokenData() {
    }

    private TokenData(TokenData.Builder builder) {
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
        this.accessTokenExpiredAt = builder.accessTokenExpiredAt;
        this.refreshTokenExpiredAt = builder.refreshTokenExpiredAt;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getAccessTokenExpiredAt() {
        return this.accessTokenExpiredAt;
    }

    public void setAccessTokenExpiredAt(Date accessTokenExpiredAt) {
        this.accessTokenExpiredAt = accessTokenExpiredAt;
    }

    public Date getRefreshTokenExpiredAt() {
        return this.refreshTokenExpiredAt;
    }

    public void setRefreshTokenExpiredAt(Date refreshTokenExpiredAt) {
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }

    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private Date accessTokenExpiredAt;
        private Date refreshTokenExpiredAt;

        public Builder(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public TokenData.Builder accessTokenExpiredAt(Date accessTokenExpiredAt) {
            this.accessTokenExpiredAt = accessTokenExpiredAt;
            return this;
        }

        public TokenData.Builder refreshTokenExpiredAt(Date refreshTokenExpiredAt) {
            this.refreshTokenExpiredAt = refreshTokenExpiredAt;
            return this;
        }

        public TokenData build() {
            return new TokenData(this);
        }
    }
}
