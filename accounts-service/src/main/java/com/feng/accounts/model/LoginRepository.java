package com.feng.accounts.model;

import java.util.Optional;

public interface LoginRepository {

    void add(Login login);

    Optional<Login> findByUserName(Username username);

    Login findByEmailAddress(EmailAddress email);
}
