package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.Membership;
import com.feng.accounts.model.TenantId;
import com.feng.accounts.model.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMembershipRepositoryHelper extends JpaRepository<Membership, Long> {
    Optional<Membership> findByTenantIdAndUsername(TenantId tenantId, Username username);
}
