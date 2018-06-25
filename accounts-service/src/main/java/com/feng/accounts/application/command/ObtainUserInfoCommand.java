package com.feng.accounts.application.command;

import lombok.Data;
import lombok.ToString;

/**
 * Created by fengshuaiju on 2018-06-25.
 */
@Data
@ToString
public class ObtainUserInfoCommand {

    private String country;
    private int gender = 1;
    private String province;
    private String city;
    private String avatarUrl;
    private String nickName;
    private String language;

}
