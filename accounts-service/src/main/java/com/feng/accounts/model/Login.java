package com.feng.accounts.model;

import com.feng.accounts.support.persistence.IdentifiedDomainObject;
import com.feng.accounts.support.utils.Validate;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;

@Entity
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "login")
@Accessors(fluent = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Login extends IdentifiedDomainObject {

    private Username username;

    private String password;

    private Cellphone cellphone;

    private EmailAddress emailAddress;

    private WechatOpenId wechatOpenId;

    @Column(updatable = false, insertable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public Login(Username username, Cellphone cellphone, String password, User user) {
        this.username = Validate.notNull(username);
        this.cellphone = Validate.notNull(cellphone);
        this.password = password;
        this.user = user;
    }

    public Login(Username username, EmailAddress emailAddress, String password, User user) {
        this.username = Validate.notNull(username);
        this.emailAddress = Validate.notNull(emailAddress);
        this.password = password;
        this.user = user;
    }

    public Login(Username username, String password, User user) {
        this.username = Validate.notNull(username);
        this.password = password;
        this.user = user;
    }

    public Login(Username username, Cellphone cellphone, EmailAddress email, String password, User user, WechatOpenId wechatOpenId) {
        Validate.isTrue(!(username == null && cellphone == null && email == null),
                "Username, cellphone and email cannot be null at the same time.");
        this.username = username;
        this.cellphone = cellphone;
        this.emailAddress = email;
        this.password = password;
        this.user = Validate.notNull(user);
        this.wechatOpenId = wechatOpenId;
    }

    public void bindWechat(WechatOpenId wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    public User user(){
        return this.user;
    }

    public void editUserInfo(String nickname, Integer sex,
                             String headImageUrl, String country, String province, String city) {

        user.editInfo(nickname, sex, headImageUrl, country, province, city);

    }

    public void setPassword(String password) {
        this.password = password;
    }
}
