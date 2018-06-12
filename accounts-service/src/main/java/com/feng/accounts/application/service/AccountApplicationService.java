package com.feng.accounts.application.service;

import com.feng.accounts.application.command.BindWechatCommand;
import com.feng.accounts.model.*;
import com.feng.accounts.support.utils.Validate;
import com.feng.accounts.support.utils.ValidationException;
import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;
import static sprout.jooq.generate.tables.Login.LOGIN;
import static sprout.jooq.generate.tables.Users.USERS;

@Service
@Slf4j
public class AccountApplicationService {

    @Autowired
    private LoginRepository loginRepository;

    private PasswordEncoder passwordEncoder;

    private DSLContext jooq;

    @Autowired
    private SmsSender smsSender;


    @Autowired
    public AccountApplicationService(DSLContext jooq) {
        this.jooq = jooq;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Transactional(readOnly = true)
    public String friendlyName(String username) {

        Record3<String, String, String> record = jooq.select(LOGIN.USERNAME, USERS.NICKNAME, USERS.NAME)
                .from(LOGIN)
                .leftOuterJoin(USERS).on(LOGIN.USER_ID.eq(USERS.ID))
                .where(LOGIN.USERNAME.eq(username)).fetchOne();
        if (record == null) {
            return null;
        }

        String _username = record.value1();
        String nickname = record.value2();
        String name = record.value3();

        if (StringUtils.isNotBlank(nickname)) {
            return nickname;
        } else {
            if (StringUtils.isNotBlank(name)) {
                return name;
            } else {
                return _username;
            }
        }

    }

    @Transactional
    public Login createUser(Username username, Cellphone cellphone, EmailAddress email, String password, String name) {
        if (username != null) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(username.toString())).fetch().isEmpty(), "error.username.existed");
        }
        if (cellphone != null) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.CELLPHONE.eq(cellphone.toString())).fetch().isEmpty(), "error.cellphone.existed");
        }
        if (email != null) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.EMAIL_ADDRESS.eq(email.toString())).fetch().isEmpty(), "error.email.existed");
        }

        if (username == null) {
            username = generateUniqueUsername();
        }

        Login login = new Login(username, cellphone, email, passwordEncoder.encode(password), new User(username, name));
        loginRepository.add(login);
        return login;
    }

    @Transactional
    public Login registerUser(String username, String password, String name) {
        Validate.notBlank(username, "Username can not be blank");

        Login login;
        Username uniqueUsername;
        if (Cellphone.isValid(username)) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.CELLPHONE.eq(username)).fetch().isEmpty(), "error.cellphone.existed");

            uniqueUsername = generateUniqueUsername();
            login = new Login(uniqueUsername, new Cellphone(username), passwordEncoder.encode(password), new User(uniqueUsername, name));
        } else if (EmailAddress.isValid(username)) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.EMAIL_ADDRESS.eq(username)).fetch().isEmpty(), "error.email.existed");

            uniqueUsername = generateUniqueUsername();
            login = new Login(uniqueUsername, new EmailAddress(username), passwordEncoder.encode(password), new User(uniqueUsername, name));
        } else {
            String regex = "^[a-zA-Z][a-zA-Z0-9_-]*$";
            if (username.matches(regex)) {
                Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(username)).fetch().isEmpty(), "error.username.existed");
                uniqueUsername = new Username(username);
                login = new Login(uniqueUsername, passwordEncoder.encode(password), new User(uniqueUsername, name));
            } else {
                throw new ValidationException("error.register.username.invalid");
            }
        }

        loginRepository.add(login);

        return login;
    }

    private Username generateUniqueUsername() {
        while (true) {
            String random = "u_" + RandomStringUtils.randomAlphabetic(16);
            if (jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(random)).fetch().isEmpty()) {
                return new Username(random);
            }
        }
    }

    @Transactional
    public void bindWechat(BindWechatCommand bindWechatCommand, Username username) {
        loginRepository.findByUserName(username)
                .ifPresent(login -> {
                    login.bindWechat(bindWechatCommand.getWechatOpenId());

                    login.editUserInfo(bindWechatCommand.getNickname(), bindWechatCommand.getSex(),
                            bindWechatCommand.getHeadImageUrl(), bindWechatCommand.getCountry(), bindWechatCommand.getProvince(),
                            bindWechatCommand.getCity());

                    log.info("User {} binding Wechat success", username);
                });
    }



    private Collection<SortField<?>> getSortFields(Table<? extends Record> table, Sort sortSpec) {
        if (sortSpec == null) {
            return Collections.emptyList();
        }

        return Streams.stream(sortSpec.iterator())
                .map(field -> {
                    try {

                        Field _field = table.getClass().getField(field.getProperty().toUpperCase());
                        TableField tableField = (TableField) _field.get(table);
                        return (SortField<?>) (field.getDirection() == Sort.Direction.ASC ? tableField.asc() : tableField.desc());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new InvalidDataAccessApiUsageException("Could not find table field: " + field.getProperty(), e);
                    }
                })
                .collect(toList());
    }



    @Transactional
    public void updatePassword(Username username, String password) {
        loginRepository.findByUserName(username)
                .ifPresent(login -> login.setPassword(passwordEncoder.encode(password)));
    }

}
