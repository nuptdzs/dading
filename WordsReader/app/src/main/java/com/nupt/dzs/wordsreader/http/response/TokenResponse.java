package com.nupt.dzs.wordsreader.http.response;

/**
 * Created by Administrator on 2016/4/26.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 * "access_token": "m4sprK03EG5Cs6iqyZNTSnPKRDe0M5",
"token_type": "Bearer",
"expires_in": 36000,
"refresh_token": "ydaIL025K2VnZCAguxNOU39NMtx8hf",
"scope": "read write"
 */
public class TokenResponse {
    private String access_token;
    private String token_type;
    private String expires_in;
    private String refresh_token;
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
