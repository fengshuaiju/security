package com.feng.accounts.application.command;

import lombok.Data;
import lombok.ToString;

/**
 * Created by fengshuaiju on 2018-06-14.
 */
@Data
@ToString
public class UserRegisterCommand {

    private String userName;
    private String password;
    private String nickName;

    private UserSource userSource = UserSource.WECHAT;

    enum UserSource{
        WECHAT,
        NORMAL
    }

}
