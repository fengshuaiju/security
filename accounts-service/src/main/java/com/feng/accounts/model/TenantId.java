package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Value
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class TenantId {

    private UUID tenantId;

    public TenantId(UUID tenantId) {
        this.tenantId = Validate.notNull(tenantId);
    }

    public TenantId(String id) {
        this.tenantId = UUID.fromString(Validate.notBlank(id));
    }

    @Override
    public String toString() {
        return tenantId.toString();
    }

}
