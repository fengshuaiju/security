package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Accessors(fluent = true)
@Value
public class RoleGroupId implements Serializable {

    @Column(name = "role_group_id")
    private UUID id;

    private RoleGroupId() {
        this.id = new UUID(0L, 0L);
    }

    public RoleGroupId(UUID id) {
        this.id = Validate.notNull(id);
    }

}
