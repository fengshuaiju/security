package com.feng.authserver.domain.model;

import com.feng.authserver.support.utils.Validate;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.regex.Pattern;

@Value
@Accessors(fluent = true)
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

}
