package com.feng.authserver.domain.model;

import com.feng.authserver.support.utils.Validate;
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

    private static final String pattern = "^[a-zA-Z\\d_-]{20,}$";

    public OpenId(String openid){
        Validate.matchesPattern(openid, pattern, "error.openId.format.invalid");
        this.openid = openid;
    }

    public static boolean isValid(String openid) {
        return Pattern.matches(pattern, openid);
    }
}
