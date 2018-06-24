package com.feng.accounts.authconfig;

import com.feng.accounts.config.CustomUser;
import com.feng.accounts.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static sprout.jooq.generate.tables.Login.LOGIN;
import static sprout.jooq.generate.tables.Users.USERS;

@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DSLContext jooq;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String NOPASSWORD = "N/A";
    private boolean fromWeChat = false;

    @Data
    private static class UserInfo {
        private String username;
        private String password;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Condition usernameCondition = null;

        if (Cellphone.isValid(username)) {
            usernameCondition = LOGIN.CELLPHONE.eq(username);
        } else if (EmailAddress.isValid(username)) {
            usernameCondition = LOGIN.EMAIL_ADDRESS.eq(username);
        } else if (WechatOpenId.isValid(username)) {
            usernameCondition = LOGIN.WECHAT_OPEN_ID.eq(username);
            fromWeChat = true;
        } else if (username.matches("^[a-zA-Z][a-zA-Z0-9_-]*$")) {
            usernameCondition = LOGIN.USERNAME.eq(username);
        }

        if (usernameCondition != null) {
            List<UserInfo> userInfos =
                    jooq.select(LOGIN.USERNAME, LOGIN.PASSWORD)
                            .from(LOGIN)
                            .where(usernameCondition)
                            .fetchInto(UserInfo.class);
            if (!userInfos.isEmpty()) {
                UserInfo user = userInfos.get(0);
                String actualUsername = user.getUsername();
                String password = !fromWeChat ? stripPrefix(user.getPassword()) : passwordEncoder.encode(NOPASSWORD);

                //查询用户信息
                String userRole = jooq.select(USERS.ROLES)
                        .from(USERS)
                        .where(USERS.USERNAME.eq(actualUsername))
                        .fetchOptional(USERS.ROLES)
                        .orElse(Role.VISITOR.name());

                List<List<String>> lists = Collections.singletonList(Arrays.asList(userRole.split(";")));

                return new CustomUser(
                        actualUsername,
                        password,
                        lists.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(toList()));
            }
        }

        throw new UsernameNotFoundException("error.username.not-found");
    }

    private String stripPrefix(String password) {
        if (StringUtils.startsWith(password, "{bcrypt}")) {
            return StringUtils.substringAfter(password, "{bcrypt}");
        }
        return password;
    }
}
