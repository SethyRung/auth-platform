package com.sethy.service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tokens {
    private String accessToken;
    private String refreshToken;
    private int accessTokenExpires;
    private int refreshTokenExpires;
}
