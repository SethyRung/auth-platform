package com.sethy.service.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
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
            summary = "Ping endpoint",
            description = "Returns 'Pong' to verify API is running"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API is healthy",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class, example = "Pong")
                    )
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong");
    }
}
