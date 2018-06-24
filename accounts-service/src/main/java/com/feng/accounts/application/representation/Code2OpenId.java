package com.feng.accounts.application.representation;

import lombok.Data;

/**
 * Created by fengshuaiju on 2018-06-24.
 */
@Data
public class Code2OpenId {
    private String openid;
    private String errcode;
    private String errmsg;
}
