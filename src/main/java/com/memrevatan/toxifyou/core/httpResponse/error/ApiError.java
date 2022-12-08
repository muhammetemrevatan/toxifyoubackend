package com.memrevatan.toxifyou.core.httpResponse.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
//import com.memrevatan.toxifyou.core.jsonView.BaseView;

import java.util.Date;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

//    @JsonView(BaseView.Base.class)
    private int status;
//    @JsonView(BaseView.Base.class)
    private String message;
//    @JsonView(BaseView.Base.class)
    private String path;
//    @JsonView(BaseView.Base.class)
    private long timestamp = new Date().getTime();
//    @JsonView(BaseView.Base.class)
    private Map<String, String> validationErrors;

    public ApiError(int httpStatus, String message, String path) {
        this.status = httpStatus;
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

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
