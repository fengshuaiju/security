package com.feng.accounts.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

//    private String tenantId;
//    private String tenantType;
//    private String tenantStatus;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                      String tenantId, String tenantType, String tenantStatus) {
        super(username, password, authorities);
//        this.tenantId = tenantId;
//        this.tenantType = tenantType;
//        this.tenantStatus = tenantStatus;
    }

    public CustomUser getCustomUser() {
        return this;
    }

}
