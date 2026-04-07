package com.example.fiver;

public class ApiResponse {
    public String message;
    public Object data;
    public ApiResponse(String message, Object data)
    {
        this.message = message;
        this.data=data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
