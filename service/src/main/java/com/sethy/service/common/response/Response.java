package com.sethy.service.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Standard API response wrapper")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class Response<T> {

    @Schema(description = "Response status information")
    private Status status;

    @Schema(description = "Response data payload")
    private T data;

    @Schema(description = "Metadata for pagination or additional info")
    private Meta meta;

    private Response() {}

    private Response(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    private Response(Status status, T data, Meta meta) {
        this.status = status;
        this.data = data;
        this.meta = meta;
    }

    public static <T> Response<T> success(T data) {
        Status status = new Status(ResponseCode.SUCCESS, "Success");
        return new Response<>(status, data);
    }

    public static <T> Response<T> success(T data, String message) {
        Status status = new Status(ResponseCode.SUCCESS, message);
        return new Response<>(status, data);
    }

    public static <T> Response<T> error(ResponseCode code, String message) {
        Status status = new Status(code, message);
        return new Response<>(status, null);
    }

    public static <T> Response<T> error(ResponseCode code, String message, T data) {
        Status status = new Status(code, message);
        return new Response<>(status, data);
    }

    public static <T> Response<T> withMeta(T data, Meta meta) {
        Status status = new Status(ResponseCode.SUCCESS, "Success");
        return new Response<>(status, data, meta);
    }

    public static <T> Response<T> withMeta(T data, Meta meta, String message) {
        Status status = new Status(ResponseCode.SUCCESS, message);
        return new Response<>(status, data, meta);
    }

    @Schema(description = "Status information")
    @Setter
    @Getter
    public static class Status {

        @Schema(description = "Response code", example = "SUCCESS")
        private ResponseCode code;

        @Schema(description = "Human-readable message", example = "Success")
        private String message;

        @Schema(description = "Unique request identifier")
        private String requestId;

        @Schema(description = "Request timestamp in milliseconds", example = "1642245600000")
        private long requestTime;

        public Status(ResponseCode code, String message) {
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
