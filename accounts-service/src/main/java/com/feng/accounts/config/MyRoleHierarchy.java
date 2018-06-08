package com.feng.accounts.config;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

public enum MyRoleHierarchy {
    INSTANCE;

    MyRoleHierarchy() {
        roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                "ROLE_PLATFORM_ACCOUNT_ADMIN > ROLE_PLATFORM_TENANT_ACCOUNT_MANAGEMENT"
        );
    }

    private RoleHierarchyImpl roleHierarchy;

    public RoleHierarchy get() {
        return this.roleHierarchy;
    }
}
