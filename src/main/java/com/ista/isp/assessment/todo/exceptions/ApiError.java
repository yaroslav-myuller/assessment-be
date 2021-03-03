package com.ista.isp.assessment.todo.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Error response object for rest exception handling
 */
public class ApiError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> details;

    private ApiError() {
        this.timestamp = LocalDateTime.now();
        this.message = "Unexpected error";
    }

    public ApiError(HttpStatus httpStatus, String path) {
        this();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.path = path;
    }

    public ApiError(HttpStatus httpStatus, String message, String path) {
        this(httpStatus, path);
        this.message = message;
        this.path = path;
    }

    public ApiError(HttpStatus httpStatus, String message, String path, List<String> details) {
        this(httpStatus, message, path);
        this.details = Collections.unmodifiableList(details);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiError)) return false;
        ApiError apiError = (ApiError) o;
        return Objects.equals(timestamp, apiError.timestamp) && Objects.equals(status, apiError.status) && Objects.equals(error, apiError.error) && Objects.equals(message, apiError.message) && Objects.equals(path, apiError.path) && Objects.equals(details, apiError.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, error, message, path, details);
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", details=" + details +
                '}';
    }
}
