package jp.asatex.quickapp.kyuyokeisan_backend_service.controller.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 错误响应 DTO
 * 用于统一返回错误信息
 */
public class ErrorResponseDto {

    /**
     * 错误时间戳
     */
    private LocalDateTime timestamp;

    /**
     * HTTP 状态码
     */
    private Integer status;

    /**
     * 错误类型
     */
    private String error;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 请求路径
     */
    private String path;

    // 默认构造函数
    public ErrorResponseDto() {
    }

    // 全参构造函数
    public ErrorResponseDto(LocalDateTime timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getter 和 Setter 方法（流式风格）
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ErrorResponseDto setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ErrorResponseDto setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public ErrorResponseDto setError(String error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ErrorResponseDto setPath(String path) {
        this.path = path;
        return this;
    }

    // 流式编程风格的 with 方法，返回新实例（不可变风格）
    public ErrorResponseDto withTimestamp(LocalDateTime timestamp) {
        return new ErrorResponseDto(timestamp, this.status, this.error, this.message, this.path);
    }

    public ErrorResponseDto withStatus(Integer status) {
        return new ErrorResponseDto(this.timestamp, status, this.error, this.message, this.path);
    }

    public ErrorResponseDto withError(String error) {
        return new ErrorResponseDto(this.timestamp, this.status, error, this.message, this.path);
    }

    public ErrorResponseDto withMessage(String message) {
        return new ErrorResponseDto(this.timestamp, this.status, this.error, message, this.path);
    }

    public ErrorResponseDto withPath(String path) {
        return new ErrorResponseDto(this.timestamp, this.status, this.error, this.message, path);
    }

    // 流式编程风格的 Builder 模式
    public static ErrorResponseDtoBuilder builder() {
        return new ErrorResponseDtoBuilder();
    }

    public static class ErrorResponseDtoBuilder {
        private LocalDateTime timestamp;
        private Integer status;
        private String error;
        private String message;
        private String path;

        public ErrorResponseDtoBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseDtoBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public ErrorResponseDtoBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResponseDtoBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseDtoBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponseDto build() {
            return new ErrorResponseDto(timestamp, status, error, message, path);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponseDto that = (ErrorResponseDto) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(message, that.message) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, error, message, path);
    }

    @Override
    public String toString() {
        return "ErrorResponseDto{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

