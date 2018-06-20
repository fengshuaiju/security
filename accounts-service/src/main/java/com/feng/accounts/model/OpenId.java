package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.regex.Pattern;

/**
 * Created by fengshuaiju on 2018/1/11.
 */

@Value
@Accessors(fluent = true)
public class OpenId {

    private static final long serialVersionUID = -2020330093795460858L;
    private String openid;

    public OpenId(String openid){
        Validate.matchesPattern(openid, "^[a-zA-Z\\d_]{20,}$", "error.openId.format.invalid");
        this.openid = openid;
    }

    public static boolean isValid(String openid) {
        return Pattern.matches("^[a-zA-Z\\d_]{20,}$", openid);
    }
}
