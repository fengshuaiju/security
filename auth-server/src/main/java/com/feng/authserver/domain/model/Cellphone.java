package com.feng.authserver.domain.model;

import com.feng.authserver.support.utils.Validate;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.regex.Pattern;

@Value
@Accessors(fluent = true)
public class Cellphone {

    private static final long serialVersionUID = -2020330093795460858L;
    private String cellphone;

    public Cellphone(String cellphone) {
        Validate.matchesPattern(cellphone, "^1\\d{10}$", "error.cellphone.format.invalid");
        this.cellphone = cellphone;
    }

    public static boolean isValid(String cellphone) {
        return Pattern.matches("^1\\d{10}$", cellphone);
    }

}
