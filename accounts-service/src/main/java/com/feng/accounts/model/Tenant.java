package com.feng.accounts.model;

import com.feng.accounts.support.domain.DomainEventPublisher;
import com.feng.accounts.support.persistence.IdentifiedDomainObject;
import com.feng.accounts.support.utils.Validate;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public class Tenant extends IdentifiedDomainObject {

    @Getter
    private TenantId tenantId;

    @Column(length = 8)
    private String code;

    @AttributeOverride(name = "username", column = @Column(name = "owner_id"))
    @Getter
    private Username ownerId;

    @AttributeOverride(name = "username", column = @Column(name = "supporter_id"))
    private Username supporterId;

    private String chineseName;

    private String englishName;

    private String officeAddress;

    private String officePhone;

    private String faxNumber;

    private String postcode;

    private BusinessLicenseNumber businessLicenseNumber;

    private Certificates certificates;

    @Getter
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(updatable = false, insertable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant reviewedAt;

    private String reviewRemarks;

    public RoleGroup newRoleGroup(String name, List<Role> roles) {
        return new RoleGroup(new RoleGroupId(UUID.randomUUID()), tenantId, name, roles);
    }

    public Membership newMembership(Login login, List<RoleGroupId> roleGroupIds) {
        return new Membership(login, tenantId, roleGroupIds);
    }

    @Embeddable
    @Value
    @Accessors(fluent = true)
    @Getter
    @Builder
    public static class Certificates {
        private String businessLicenseUri;
        private String organizationCodeUri;
        private String taxNumberUri;
    }

    public enum Status {
        PENDING_APPROVE,
        APPROVED,
        DISAPPROVED,
        DISABLED
    }

    public enum Type {
        PREMIER_CUSTOMER,
        FREIGHT_FORWARDER,
        PLATFORM
    }

    public Tenant(TenantId tenantId, Username ownerId, String chineseName) {
        this.tenantId = Validate.notNull(tenantId);
        this.ownerId = ownerId;
        this.chineseName = Validate.notBlank(chineseName, "error.tenant.chineseName.blank");
        this.status = Status.PENDING_APPROVE;
    }

    public void approve(Username supporterId, Type type, String code, String reviewRemarks) {
        Validate.isTrue(StringUtils.isNotBlank(code) && StringUtils.length(code) <= 8,
                "error.tenant.code.length.invalid");
        this.code = code.toUpperCase();

        this.type = Validate.notNull(type);
        this.supporterId = Validate.notNull(supporterId);
        this.status = Status.APPROVED;
        this.reviewRemarks = reviewRemarks;
        this.reviewedAt = Instant.now();

        DomainEventPublisher.publish(
                TenantApproved.builder()
                        .id(this.tenantId.toString())
                        .code(this.code)
                        .type(this.type)
                        .chineseName(this.chineseName)
                        .build()
        );
    }

    public void disapprove(String reviewRemarks) {
        this.status = Status.DISAPPROVED;
        this.reviewRemarks = reviewRemarks;
        this.reviewedAt = Instant.now();
    }

    public void enable(boolean enabled) {
        this.status = enabled ? Status.APPROVED : Status.DISABLED;

        DomainEventPublisher.publish(new TenantEnabled(this.tenantId.tenantId().toString(), enabled));
    }

    public void updateInfo(String tenantChineseName) {
        this.chineseName = tenantChineseName;
    }
}
