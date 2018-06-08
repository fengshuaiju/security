package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

/**
 * Created by fengshuaiju on 2018/1/11.
 */

@Embeddable
@Value
@Accessors(fluent = true)
public class WechatOpenId {

    private static final long serialVersionUID = -2020330093795460858L;
    private String wechatOpenId;

    public WechatOpenId(String wechatOpenId){
        Validate.matchesPattern(wechatOpenId, "^[a-zA-Z0-9_-]{20,}$", "error.openId.format.invalid");
        this.wechatOpenId = wechatOpenId;
    }

    public static boolean isValid(String wechatOpenId) {
        return Pattern.matches("^[a-zA-Z0-9_-]{20,}$", wechatOpenId);
    }
}
