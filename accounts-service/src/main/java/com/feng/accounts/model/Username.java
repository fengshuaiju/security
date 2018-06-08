package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Embeddable
@Value
@Accessors(fluent = true)
public class Username {

    private String username;

    public Username(String username) {
        Validate.notBlank(username, "username can not be blank.");
        Validate.isTrue(username.matches("^[a-zA-Z][a-zA-Z0-9_-]*$"), "error.register.username.invalid");
        this.username = username;
    }

    @Override
    public String toString() {
        return this.username;
    }

}
