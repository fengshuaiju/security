package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@Value
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class EmailAddress {

    private static final long serialVersionUID = 6791553173939541163L;
    private String emailAddress;

    public EmailAddress(String emailAddress) {
        Validate.matchesPattern(emailAddress, "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", "error.email.format.invalid");
        this.emailAddress = emailAddress;
    }

    public static boolean isValid(String email) {
        return Pattern.matches("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", email);
    }

    @Override
    public String toString() {
        return emailAddress;
    }

}
