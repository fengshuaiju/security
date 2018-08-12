package com.feng.accounts.model.event;

import com.feng.accounts.support.domain.DomainEvent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoUpdated extends DomainEvent{

    private String username;

    private String country;
    private int gender;
    private String province;
    private String city;
    private String avatarUrl;
    private String nickName;
    private String language;

}
