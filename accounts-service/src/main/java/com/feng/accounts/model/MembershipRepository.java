package com.feng.accounts.model;

import java.util.Optional;

public interface MembershipRepository {
    
    void add(Membership membership);

    Optional<Membership> get(TenantId tenantId, Username username);

    void delete(Membership membership);
}
