package com.test.boot.helloJPA;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

public class CustomResponse {
    Boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    public CustomResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CustomResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
