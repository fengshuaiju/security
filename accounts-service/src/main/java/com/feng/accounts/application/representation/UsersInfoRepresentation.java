package com.feng.accounts.application.representation;

import com.feng.accounts.model.Sex;
import lombok.Builder;
import lombok.Data;

/**
 * Created by fengshuaiju on 2018-06-14.
 */
@Data
@Builder
public class UsersInfoRepresentation {

    private String username;
    private String nickname;

    private Sex sex;
    private String city;
    private String province;
    private String country;

    private String headImageUrl;

    public UsersInfoRepresentation(String username, String nickname, Sex sex, String city,
            String province, String country, String headImageUrl) {
        this.username = username;
        this.nickname = nickname;
        this.sex = sex;

        this.city = city;
        this.province = province;
        this.country = country;

        this.headImageUrl = headImageUrl;
    }
}
