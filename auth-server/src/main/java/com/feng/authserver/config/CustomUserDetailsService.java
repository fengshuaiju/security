package com.feng.authserver.config;

import com.feng.authserver.domain.model.Cellphone;
import com.feng.authserver.domain.model.EmailAddress;
import com.feng.authserver.domain.model.OpenId;
import com.feng.authserver.domain.model.Role;
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

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;
import static sprout.jooq.generate.tables.Login.LOGIN;
import static sprout.jooq.generate.tables.Membership.MEMBERSHIP;
import static sprout.jooq.generate.tables.MembershipRoleGroupIds.MEMBERSHIP_ROLE_GROUP_IDS;
import static sprout.jooq.generate.tables.RoleGroupRoles.ROLE_GROUP_ROLES;
import static sprout.jooq.generate.tables.Tenant.TENANT;

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
        private UUID tenantId;
        private String type;
        private String status;
        private long id;
        private boolean enabled;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Condition usernameCondition = null;

        if (Cellphone.isValid(username)) {
            usernameCondition = LOGIN.CELLPHONE.eq(username);
        } else if (EmailAddress.isValid(username)) {
            usernameCondition = LOGIN.EMAIL_ADDRESS.eq(username);
        } else if (OpenId.isValid(username)) {
            usernameCondition = LOGIN.WECHAT_OPEN_ID.eq(username);
            fromWeChat = true;
        } else if (username.matches("^[a-zA-Z][a-zA-Z0-9_-]*$")) {
            usernameCondition = LOGIN.USERNAME.eq(username);
        }

        if (usernameCondition != null) {
            List<UserInfo> userInfos =
                    jooq.select(LOGIN.USERNAME, LOGIN.PASSWORD, TENANT.TENANT_ID, TENANT.TYPE,
                            TENANT.STATUS, MEMBERSHIP.ID, MEMBERSHIP.ENABLED)
                            .from(LOGIN)
                            .leftOuterJoin(MEMBERSHIP).on(LOGIN.USERNAME.eq(MEMBERSHIP.USERNAME))
                            .leftOuterJoin(TENANT).on(MEMBERSHIP.TENANT_ID.eq(TENANT.TENANT_ID))
                            .where(usernameCondition)
                            .fetchInto(UserInfo.class);
            if (!userInfos.isEmpty()) {
                UserInfo user = userInfos.get(0);
                String actualUsername = user.getUsername();
                String password = !fromWeChat ? stripPrefix(user.getPassword()) : passwordEncoder.encode(NOPASSWORD);
                final String[] tenantId = {"[None]"};
                final String[] tenantType = {"N/A"};
                final String[] tenantStatus = {""};
                AtomicReference<List<String>> roles = new AtomicReference<>(Collections.singletonList(Role.VISITOR.name()));

                userInfos.stream()
//                        .filter(u -> StringUtils.equalsIgnoreCase(u.status, "APPROVED"))
                        .filter(u -> u.enabled)
                        .findFirst()
                        .ifPresent(tenantUser -> {
                            tenantId[0] = tenantUser.tenantId.toString();
                            tenantType[0] = tenantUser.type == null ? "[NOT DETERMINED]" : tenantUser.type;
                            tenantStatus[0] = tenantUser.status;
                            roles.set(jooq.selectDistinct(ROLE_GROUP_ROLES.ROLE)
                                    .from(ROLE_GROUP_ROLES)
                                    .where(ROLE_GROUP_ROLES.ROLE_GROUP_ID.in(
                                            jooq.select(MEMBERSHIP_ROLE_GROUP_IDS.ROLE_GROUP_ID)
                                                    .from(MEMBERSHIP_ROLE_GROUP_IDS)
                                                    .where(MEMBERSHIP_ROLE_GROUP_IDS.MEMBERSHIP_ID.eq(tenantUser.id))
                                    ))
                                    .fetchInto(String.class));
                        });

                return new CustomUser(
                        actualUsername,
                        password,
                        roles.get().stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .collect(toList()),
                        tenantId[0],
                        tenantType[0],
                        tenantStatus[0]);
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
