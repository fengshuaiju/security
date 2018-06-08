package com.feng.accounts.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

/**
 * Created by fengshuaiju on 2018/1/11.
 */

@Embeddable
@Value
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Region {

    private String city;

    private String province;

    private String country;


    public Region(String city, String province, String country){
        this.city = city;
        this.province = province;
        this.country = country;
    }

}
