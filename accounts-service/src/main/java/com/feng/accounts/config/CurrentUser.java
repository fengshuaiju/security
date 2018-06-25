package com.feng.accounts.config;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by fengshuaiju on 2018-06-25.
 */
public class CurrentUser {

    public static String getUserName(){
        return (String)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

}
