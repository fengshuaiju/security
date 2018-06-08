package com.feng.accounts.model;

import com.feng.accounts.support.domain.DomainEventPublisher;
import com.feng.accounts.support.persistence.ConcurrencySafeEntity;
import com.feng.accounts.support.utils.Validate;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.time.Instant;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = false)
@ToString
@Accessors(fluent = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership extends ConcurrencySafeEntity {

    private Username username;

    private TenantId tenantId;

    private String name;

    private Cellphone cellphone;

    private EmailAddress emailAddress;

    @ElementCollection
    private List<RoleGroupId> roleGroupIds;

    private String remarks;

    @Column(updatable = false, insertable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    private boolean enabled;

    public Membership(Login user, TenantId tenantId, List<RoleGroupId> roleGroupIds) {
        Validate.notNull(user);
        this.username = Validate.notNull(user.username());
        this.name = user.user().name();
        this.cellphone = user.cellphone();
        this.emailAddress = user.emailAddress();
        this.tenantId = Validate.notNull(tenantId);
        this.roleGroupIds = Validate.notEmpty(roleGroupIds);
        this.enabled = true;

        DomainEventPublisher.publish(MemberCreated.builder()
                .tenantId(this.tenantId.toString())
                .username(this.username.toString())
                .name(this.name)
                .cellphone(this.cellphone == null ? null : this.cellphone.toString())
                .emailAddress(this.emailAddress == null ? null : this.emailAddress.toString())
                .build()
        );
    }

}
