package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.Tenant;
import com.feng.accounts.model.TenantId;
import com.feng.accounts.model.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaTenantRepository implements TenantRepository {

    private final JpaTenantRepositoryHelper repositoryHelper;

    @Autowired
    public JpaTenantRepository(JpaTenantRepositoryHelper repositoryHelper) {
        this.repositoryHelper = repositoryHelper;
    }

    @Override
    public TenantId nextId() {
        return new TenantId(UUID.randomUUID().toString());
    }

    @Override
    public Tenant add(Tenant tenant) {
        return repositoryHelper.save(tenant);
    }

    @Override
    public Optional<Tenant> findByTenantId(TenantId tenantId) {
        return repositoryHelper.findByTenantId(tenantId);
    }

    @Override
    public long count() {
        return repositoryHelper.count();
    }

}
