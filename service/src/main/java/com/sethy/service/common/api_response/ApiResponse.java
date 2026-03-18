package com.sethy.service.common.api_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Standard API response wrapper")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ApiResponse<T> {

    @Schema(description = "Response status information")
    private Status status;

    @Schema(description = "Response data payload")
    private T data;

    @Schema(description = "Metadata for pagination or additional info")
    private Meta meta;

    private ApiResponse() {}

    private ApiResponse(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    private ApiResponse(Status status, T data, Meta meta) {
        this.status = status;
        this.data = data;
        this.meta = meta;
    }

    public static <T> ApiResponse<T> success(T data) {
        Status status = new Status(ApiResponseCode.SUCCESS, "Success");
        return new ApiResponse<>(status, data);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        Status status = new Status(ApiResponseCode.SUCCESS, message);
        return new ApiResponse<>(status, data);
    }

    public static <T> ApiResponse<T> error(ApiResponseCode code, String message) {
        Status status = new Status(code, message);
        return new ApiResponse<>(status, null);
    }

    public static <T> ApiResponse<T> error(ApiResponseCode code, String message, T data) {
        Status status = new Status(code, message);
        return new ApiResponse<>(status, data);
    }

    public static <T> ApiResponse<T> withMeta(T data, Meta meta) {
        Status status = new Status(ApiResponseCode.SUCCESS, "Success");
        return new ApiResponse<>(status, data, meta);
    }

    public static <T> ApiResponse<T> withMeta(T data, Meta meta, String message) {
        Status status = new Status(ApiResponseCode.SUCCESS, message);
        return new ApiResponse<>(status, data, meta);
    }

    @Schema(description = "Status information")
    @Setter
    @Getter
    public static class Status {

        @Schema(description = "Response code", example = "SUCCESS")
        private ApiResponseCode code;

        @Schema(description = "Human-readable message", example = "Success")
        private String message;

        @Schema(description = "Unique request identifier")
        private String requestId;

        @Schema(description = "Request timestamp in milliseconds", example = "1642245600000")
        private long requestTime;

        public Status(ApiResponseCode code, String message) {
            this.code = code;
            this.message = message;
            this.requestId = generateRequestId();
            this.requestTime = System.currentTimeMillis();
        }

        private String generateRequestId() {
            return java.util.UUID.randomUUID().toString();
        }
    }

    @Schema(description = "Metadata for pagination")
    @Setter
    @Getter
    public static class Meta {

        @Schema(description = "Total number of items", example = "100")
        private long total;

        @Schema(description = "Maximum number of items per page", example = "20")
        private int limit;

        @Schema(description = "Number of items to skip", example = "0")
        private int offset;

        public Meta(long total, int limit, int offset) {
            this.total = total;
            this.limit = limit;
            this.offset = offset;
        }
    }
}
