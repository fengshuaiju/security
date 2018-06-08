package com.feng.accounts.application.representation;

import lombok.Value;

@Value
public class TenantRpcRepresentation {
    private String chineseName;
    private String type;
    private String status;
}
