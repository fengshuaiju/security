package com.feng.accounts.application.command;

import com.feng.accounts.model.Nickname;
import com.feng.accounts.model.WechatOpenId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by fengshuaiju on 2018/1/10.
 */
@Data
@NoArgsConstructor
public class BindWechatCommand {

    @NonNull
    private WechatOpenId wechatOpenId;

    private Nickname nickname;

    private Integer sex;

    private String city;

    private String province;

    private String country;

    private String headImageUrl;

}
