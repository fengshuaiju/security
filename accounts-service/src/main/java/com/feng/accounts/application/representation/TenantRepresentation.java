package com.feng.accounts.application.representation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantRepresentation {
    private String tenantId;
    private String chineseName;
    private String code;
    private Instant createdAt;
    private String cellphone;
    private String status;
    private String reviewRemarks;
}
