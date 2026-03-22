package com.sethy.service.auth.service;

import com.sethy.service.auth.dto.Tokens;
import com.sethy.service.auth.dto.UserInfo;
import com.sethy.service.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    String buildLoginUrl();

    Tokens exchangeCodeForTokens(String code) throws Exception;

    Tokens refreshToken(HttpServletRequest request) throws Exception;

    void logout(HttpServletRequest request) throws Exception;

    UserInfo getCurrentUser(HttpServletRequest request) throws Exception;

    boolean checkAuth(HttpServletRequest request);
}
