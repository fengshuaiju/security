package com.feng.accounts.application.command;

import lombok.Data;

@Data
public class CreateTenantCommand {
    private String adminUsername;
    private String password;
    private String tenantChineseName;
    private String remarks;
}
