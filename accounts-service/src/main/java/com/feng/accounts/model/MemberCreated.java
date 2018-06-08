package com.feng.accounts.model;

import com.feng.accounts.support.domain.DomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@Builder
class MemberCreated extends DomainEvent {
    private String tenantId;
    private String username;
    private String name;
    private String cellphone;
    private String emailAddress;
}
