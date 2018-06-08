package com.feng.accounts.application.command;

import lombok.Data;

@Data
public class ApproveTenantCommand {
    boolean approved;
    String code;
    String remarks;
}
