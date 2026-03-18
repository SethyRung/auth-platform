package com.sethy.service.auth.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String email;
    private JsonNode roles;
}
