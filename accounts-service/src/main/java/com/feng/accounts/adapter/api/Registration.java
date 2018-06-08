package com.feng.accounts.adapter.api;

import lombok.Data;

@Data
public class Registration {
    private String username;
    private String password;
    private String tenantName;
    private String captcha;
}
