package com.memrevatan.toxifyou.core.httpResponse.success;

import java.util.Date;
import java.util.Map;

public class ApiSuccess {
    private int status;
    private String message;
    private String path;
    private long timestamp = new Date().getTime();
    private Map<String, String> validationSuccess;

    public ApiSuccess(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getValidationSuccess() {
        return validationSuccess;
    }

    public void setValidationSuccess(Map<String, String> validationSuccess) {
        this.validationSuccess = validationSuccess;
    }
}
