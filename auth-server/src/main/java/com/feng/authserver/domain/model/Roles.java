package com.feng.authserver.domain.model;

import com.feng.authserver.support.utils.Validate;
import lombok.Value;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Value
@Accessors(fluent = true)
public class Roles {

    private String roles;

    public Roles(Role... roles) {
        Validate.notEmpty(roles);
        this.roles = Arrays.stream(roles)
                .distinct()
                .map(Role::name)
                .collect(joining(","));
    }

    public Roles(String rolesStr) {
        this.roles = org.apache.commons.lang3.Validate.notBlank(rolesStr);
    }

    public List<Role> roles() {
        return StringUtils.isBlank(roles)
                ? Collections.emptyList()
                : Arrays.stream(roles.split(",")).map(Role::valueOf).collect(toList());
    }

}
