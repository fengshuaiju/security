package com.feng.accounts.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Profile({"staging", "production"})
public class PlatformInitializationService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AccountApplicationService accountApplicationService;


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

    }

}
