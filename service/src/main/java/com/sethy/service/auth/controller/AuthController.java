package com.sethy.service.auth.controller;

import com.sethy.service.auth.dto.Tokens;
import com.sethy.service.auth.dto.UserInfo;
import com.sethy.service.auth.service.AuthService;
import com.sethy.service.common.api_response.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${spring.web.base-url}")
    private String webBaseUrl;

    private final String ACCESS_TOKEN_PATH = "/";
    private final String REFRESH_TOKEN_PATH = "/";

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws Exception {
        String redirectUrl = authService.buildLoginUrl();
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/callback")
    public void callback(
            @RequestParam String code,
            HttpServletResponse response
    ) throws Exception {
        Tokens tokens = authService.exchangeCodeForTokens(code);
        setTokenCookies(response, tokens);

        response.sendRedirect(webBaseUrl);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Tokens tokens = authService.refreshToken(request);
        setTokenCookies(response, tokens);

        return ResponseEntity.ok(ApiResponse.success("Tokens refreshed successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authService.logout(request);

        ResponseCookie clearAccess = ResponseCookie.from("access_token", "")
                .maxAge(0)
                .path(ACCESS_TOKEN_PATH)
                .build();

        ResponseCookie clearRefresh = ResponseCookie.from("refresh_token", "")
                .maxAge(0)
                .path(REFRESH_TOKEN_PATH)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, clearAccess.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, clearRefresh.toString());

        return ResponseEntity.ok(ApiResponse.success("User logged out successfully"));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<UserInfo>> me(HttpServletRequest request) throws Exception {
        UserInfo user = authService.getCurrentUser(request);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    private void setTokenCookies(HttpServletResponse response, Tokens tokens) {
        ResponseCookie accessCookie = ResponseCookie.from("access_token", tokens.getAccessToken())
                                        .httpOnly(true)
                                        .secure(true)
                                        .path(ACCESS_TOKEN_PATH)
                                        .maxAge(tokens.getAccessTokenExpires())
                                        .sameSite("Strict")
                                        .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokens.getRefreshToken())
                                        .httpOnly(true)
                                        .secure(true)
                                        .path(REFRESH_TOKEN_PATH)
                                        .maxAge(tokens.getRefreshTokenExpires())
                                        .sameSite("Strict")
                                        .build();
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}
