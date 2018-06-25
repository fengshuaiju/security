package com.feng.accounts.application.command;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by fengshuaiju on 2018-06-25.
 */
@Data
@ToString
@Accessors(fluent = true)
public class ObtainUserInfoCommand {

    private String country;
    private int gender;
    private String province;
    private String city;
    private String avatarUrl;
    private String nickName;
    private String language;

}
