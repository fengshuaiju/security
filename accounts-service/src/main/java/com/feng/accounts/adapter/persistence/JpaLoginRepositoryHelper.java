package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.EmailAddress;
import com.feng.accounts.model.Login;
import com.feng.accounts.model.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLoginRepositoryHelper extends JpaRepository<Login, Long> {
    Optional<Login> findByUsername(Username username);

    Login findByEmailAddress(EmailAddress email);
}
