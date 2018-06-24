package com.feng.accounts.model;

import com.feng.accounts.support.persistence.IdentifiedDomainObject;
import com.feng.accounts.support.utils.ItemsConverter;
import com.feng.accounts.support.utils.Validate;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;

/**
 * Created by fengshuaiju on 2018/1/10.
 */

@Entity
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "users")
@Accessors(fluent = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends IdentifiedDomainObject {

    private Username username;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private Region region;

    private String headImageUrl;

    @Convert(converter = ItemsConverter.class)
    private Set<String> roles = Collections.EMPTY_SET;

    @Column(updatable = false, insertable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    public User(Username username) {
        this(username, null);
    }

    public User(Username username, String nickname) {
        this.username = Validate.notNull(username);
        this.nickname = nickname;
    }

    public void editInfo(String nickname, Integer sex,
                         String headImageUrl, String country, String province, String city) {
        if(this.nickname == null){
            this.nickname = nickname;
        }

        if(this.sex == null){
            this.sex = sex == 1 ? Sex.MALE : Sex.FEMALE;
        }

        if(this.headImageUrl != null){
            this.headImageUrl = headImageUrl;
        }

        this.region = new Region(city, province, country);

    }
}
