package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.RoleGroup;
import com.feng.accounts.model.RoleGroupRepository;
import com.feng.accounts.model.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaRoleGroupRepository implements RoleGroupRepository {

    private final JpaRoleGroupRepositoryHelper repositoryHelper;

    @Autowired
    public JpaRoleGroupRepository(JpaRoleGroupRepositoryHelper repositoryHelper) {
        this.repositoryHelper = repositoryHelper;
    }

    @Override
    public void add(RoleGroup roleGroup) {
        repositoryHelper.save(roleGroup);
    }

    @Override
    public Optional<RoleGroup> get(TenantId tenantId, String name) {
        return repositoryHelper.findByTenantIdAndName(tenantId, name);
    }

    @Override
    public List<RoleGroup> get(TenantId tenantId, List<String> names) {
        return repositoryHelper.findByTenantIdAndNameIn(tenantId, names);
    }

}
