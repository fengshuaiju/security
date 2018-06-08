package com.feng.accounts.model;

import java.util.Optional;

public interface TenantRepository {
    TenantId nextId();
    Tenant add(Tenant tenant);
    Optional<Tenant> findByTenantId(TenantId tenantId);
    long count();
}
