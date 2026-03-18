package com.sethy.service.ping.controller;

import com.sethy.service.common.api_response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
@Tag(name = "Health", description = "Health check endpoints")
public class PingController {
    @GetMapping("/ping")
    @Operation(
            description = "Returns 'Pong' to verify API is running"
    )
    public ResponseEntity<ApiResponse<String>> ping() {
        return ResponseEntity.ok(ApiResponse.success("Pong"));
    }
}
