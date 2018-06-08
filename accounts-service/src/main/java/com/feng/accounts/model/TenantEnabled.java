package com.feng.accounts.model;

import com.feng.accounts.support.domain.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class TenantEnabled extends DomainEvent {
    private String tenantId;
    private boolean enabled;
}
