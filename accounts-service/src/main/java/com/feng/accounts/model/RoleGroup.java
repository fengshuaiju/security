package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleGroup {

    @Version
    private long version;

    @Getter
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id"))
    private RoleGroupId id;

    private TenantId tenantId;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<Role> roles;

    public RoleGroup(RoleGroupId roleGroupId, TenantId tenantId, String name, List<Role> roles) {
        this.id = Validate.notNull(roleGroupId);
        this.tenantId = Validate.notNull(tenantId);
        this.name = Validate.notBlank(name);
        this.roles = Validate.notEmpty(roles);
    }

}
