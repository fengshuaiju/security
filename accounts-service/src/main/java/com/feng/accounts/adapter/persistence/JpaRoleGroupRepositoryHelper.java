package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.RoleGroup;
import com.feng.accounts.model.RoleGroupId;
import com.feng.accounts.model.TenantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRoleGroupRepositoryHelper extends JpaRepository<RoleGroup, RoleGroupId> {
    Optional<RoleGroup> findByTenantIdAndName(TenantId tenantId, String name);

    List<RoleGroup> findByTenantIdAndNameIn(TenantId tenantId, List<String> names);
}
