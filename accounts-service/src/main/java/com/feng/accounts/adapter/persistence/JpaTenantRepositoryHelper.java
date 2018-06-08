package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.Tenant;
import com.feng.accounts.model.TenantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTenantRepositoryHelper extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByTenantId(TenantId tenantId);
}
