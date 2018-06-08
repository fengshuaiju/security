package com.feng.accounts.model;

import java.util.List;
import java.util.Optional;

public interface RoleGroupRepository {
    void add(RoleGroup roleGroup);
    Optional<RoleGroup> get(TenantId tenantId, String name);
    List<RoleGroup> get(TenantId tenantId, List<String> names);
}
