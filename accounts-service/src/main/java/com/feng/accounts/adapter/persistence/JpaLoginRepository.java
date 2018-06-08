package com.feng.accounts.adapter.persistence;

import com.feng.accounts.model.EmailAddress;
import com.feng.accounts.model.Login;
import com.feng.accounts.model.LoginRepository;
import com.feng.accounts.model.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaLoginRepository implements LoginRepository {

    private final JpaLoginRepositoryHelper repositoryHelper;

    @Autowired
    public JpaLoginRepository(JpaLoginRepositoryHelper repositoryHelper) {
        this.repositoryHelper = repositoryHelper;
    }

    @Override
    public void add(Login login) {
        repositoryHelper.save(login);
    }

    @Override
    public Optional<Login> findByUserName(Username username) {
        return repositoryHelper.findByUsername(username);
    }

    @Override
    public Login findByEmailAddress(EmailAddress email) {
        return repositoryHelper.findByEmailAddress(email);
    }

}
