package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.Membership;
import com.feng.accounts.model.MembershipRepository;
import com.feng.accounts.model.TenantId;
import com.feng.accounts.model.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaMembershipRepository implements MembershipRepository {

    private final JpaMembershipRepositoryHelper repositoryHelper;

    @Autowired
    public JpaMembershipRepository(JpaMembershipRepositoryHelper repositoryHelper) {
        this.repositoryHelper = repositoryHelper;
    }

    @Override
    public void add(Membership membership) {
        repositoryHelper.save(membership);
    }

    @Override
    public Optional<Membership> get(TenantId tenantId, Username username) {
        return repositoryHelper.findByTenantIdAndUsername(tenantId, username);
    }

    @Override
    public void delete(Membership membership) {
        repositoryHelper.delete(membership);
    }

}
