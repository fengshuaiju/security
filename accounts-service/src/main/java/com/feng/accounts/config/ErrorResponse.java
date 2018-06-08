package com.feng.accounts.config;

import lombok.Value;

import java.time.Instant;

@Value
public class ErrorResponse {
    private String message;
    private String path;
    private Instant timestamp;

    public ErrorResponse(String message, String path) {
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
    }
}
