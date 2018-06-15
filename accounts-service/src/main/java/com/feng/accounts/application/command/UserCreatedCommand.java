package com.feng.accounts.application.command;

import com.feng.accounts.model.Cellphone;
import com.feng.accounts.model.EmailAddress;
import com.feng.accounts.model.Username;
import com.feng.accounts.model.WechatOpenId;
import lombok.Data;

/**
 * Created by fengshuaiju on 2018-06-14.
 */
@Data
public class UserCreatedCommand {

    private Username username;
    private Cellphone cellphone;
    private EmailAddress email;
    private WechatOpenId wechatOpenId;

    private String password;
    private String nickName;

    private UserSource userSource = UserSource.WECHAT;

    enum UserSource{
        WECHAT,
        NORMAL
    }

}
