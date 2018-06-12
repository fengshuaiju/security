package com.feng.accounts.model;

import com.feng.accounts.support.domain.DomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@Builder
public class TenantApproved extends DomainEvent {
    private String id;
    private String code;
    private Tenant.Type type;
    private String chineseName;
}
