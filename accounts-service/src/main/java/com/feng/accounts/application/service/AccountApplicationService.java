package com.feng.accounts.application.service;

import com.feng.accounts.application.command.ObtainUserInfoCommand;
import com.feng.accounts.application.representation.UsersInfoRepresentation;
import com.feng.accounts.model.*;
import com.feng.accounts.support.domain.DomainEventPublisher;
import com.feng.accounts.support.utils.Validate;
import com.feng.accounts.support.utils.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static sprout.jooq.generate.tables.Login.LOGIN;
import static sprout.jooq.generate.tables.Users.USERS;

@Service
@Slf4j
public class AccountApplicationService {

    @Autowired
    private LoginRepository loginRepository;

    private PasswordEncoder passwordEncoder;

    private DSLContext jooq;

    private static Map<String, ObtainUserInfoCommand> obtainUserInfo = new ConcurrentHashMap<>();

    @Autowired
    public AccountApplicationService(DSLContext jooq) {
        this.jooq = jooq;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Transactional(readOnly = true)
    public String friendlyName(String username) {

        Record2<String, String> record = jooq.select(LOGIN.USERNAME, USERS.NICKNAME)
                .from(LOGIN)
                .leftOuterJoin(USERS).on(LOGIN.USER_ID.eq(USERS.ID))
                .where(LOGIN.USERNAME.eq(username)).fetchOne();
        if (record == null) {
            return null;
        }

        String _username = record.value1();
        String nickname = record.value2();

        if (StringUtils.isNotBlank(nickname)) {
            return nickname;
        } else {
                return _username;
            }

    }

    @Transactional
    public Login createUser(Username username, Cellphone cellphone, EmailAddress email, String password, String nickName, WechatOpenId wechatOpenId) {
        if (username != null) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(username.toString())).fetch().isEmpty(), "error.username.existed");
        }
        if (cellphone != null) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.CELLPHONE.eq(cellphone.toString())).fetch().isEmpty(), "error.cellphone.existed");
        }
        if (email != null) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.EMAIL_ADDRESS.eq(email.toString())).fetch().isEmpty(), "error.email.existed");
        }

        if (wechatOpenId != null) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.WECHAT_OPEN_ID.eq(wechatOpenId.toString())).fetch().isEmpty(), "error.wechatOpenId.existed");
        }

        if (username == null) {
            username = generateUniqueUsername();
        }

        Login login = new Login(username, cellphone, email, passwordEncoder.encode(password), new User(username, nickName), wechatOpenId);
        loginRepository.add(login);
        return login;
    }

    @Transactional
    public Login registerUser(String username, String password, String nickName) {
        Validate.notBlank(username, "Username can not be blank");

        Login login;
        Username uniqueUsername;
        if (Cellphone.isValid(username)) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.CELLPHONE.eq(username)).fetch().isEmpty(), "error.cellphone.existed");

            uniqueUsername = generateUniqueUsername();
            login = new Login(uniqueUsername, new Cellphone(username), passwordEncoder.encode(password), new User(uniqueUsername, nickName));
        } else if (EmailAddress.isValid(username)) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.EMAIL_ADDRESS.eq(username)).fetch().isEmpty(), "error.email.existed");

            uniqueUsername = generateUniqueUsername();
            login = new Login(uniqueUsername, new EmailAddress(username), passwordEncoder.encode(password), new User(uniqueUsername, nickName));
        } else if (WechatOpenId.isValid(username)) {
            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.WECHAT_OPEN_ID.eq(username)).fetch().isEmpty(), "error.wechatOpenId.existed");
            password = "N/A";
            uniqueUsername = new Username(username);
            login = new Login(uniqueUsername, passwordEncoder.encode(password), new User(uniqueUsername, nickName));
            login.bindWechat(new WechatOpenId(username));
        } else {
            String regex = "^[a-zA-Z][a-zA-Z0-9_-]*$";
            if (username.matches(regex)) {
                Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(username)).fetch().isEmpty(), "error.username.existed");
                uniqueUsername = new Username(username);
                login = new Login(uniqueUsername, passwordEncoder.encode(password), new User(uniqueUsername, nickName));
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
    public void updatePassword(Username username, String password) {
        loginRepository.findByUserName(username)
                .ifPresent(login -> login.setPassword(passwordEncoder.encode(password)));
    }

    @Transactional(readOnly = true)
    public UsersInfoRepresentation userInfo(String username) {

        Record7<String, String, String, String, String, String, String> info = jooq.select(
                LOGIN.USERNAME, USERS.NICKNAME, USERS.SEX, USERS.CITY, USERS.PROVINCE,
                USERS.COUNTRY, USERS.HEAD_IMAGE_URL)
                .from(LOGIN)
                .leftOuterJoin(USERS).on(LOGIN.USER_ID.eq(USERS.ID))
                .where(LOGIN.USERNAME.eq(username)).fetchOne();

        UsersInfoRepresentation build = UsersInfoRepresentation.builder()
                .username(info.value1())
                .nickname(info.value2())
                .sex(Sex.valueOf(info.value3()))
                .city(info.value4())
                .province(info.value5())
                .country(info.value6())
                .headImageUrl(info.value7())
                .build();

        return build;
    }

    @Transactional
    public void updateUserInfo(String username, ObtainUserInfoCommand userInfo) {
        //TODO 将用户信息放入map中定时更新
        //obtainUserInfo.put(username, userInfo);
        loginRepository.findByUserName(new Username(username))
                .ifPresent(login -> {
                    login.user().editInfo(userInfo.getNickName(),userInfo.getGender(), userInfo.getAvatarUrl(),
                    userInfo.getCountry(), userInfo.getProvince(), userInfo.getCity());
//                    DomainEventPublisher.publish();
                });
    }
}
