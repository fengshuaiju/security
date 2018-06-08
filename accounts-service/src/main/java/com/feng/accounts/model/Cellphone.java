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
public class Cellphone {

    private static final long serialVersionUID = -2020330093795460858L;
    private String cellphone;

    public Cellphone(String cellphone) {
        Validate.matchesPattern(cellphone, "^1\\d{10}$", "error.cellphone.format.invalid");
        this.cellphone = cellphone;
    }

    @Override
    public String toString() {
        return cellphone;
    }

    public static boolean isValid(String cellphone) {
        return Pattern.matches("^1\\d{10}$", cellphone);
    }

}
