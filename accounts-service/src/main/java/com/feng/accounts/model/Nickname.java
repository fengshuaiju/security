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
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Nickname {

    private String nickname;

    public Nickname(String nickname) {
        this.nickname = Validate.notBlank(nickname);
    }

}
