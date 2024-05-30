package com.greckapps.cardfront.user.error;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;


public class UserErrorResponse {
    public HttpStatus status;
    public String message;
    public LocalDateTime timestamp;

    public UserErrorResponse(){};

    public UserErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setTimestamp(LocalDateTime localDateTime) {
        this.timestamp = localDateTime;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
