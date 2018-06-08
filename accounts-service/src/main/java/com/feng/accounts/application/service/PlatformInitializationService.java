package com.feng.accounts.application.service;

import com.feng.accounts.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Slf4j
@Profile({"staging", "production"})
public class PlatformInitializationService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private AccountApplicationService accountApplicationService;


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (tenantRepository.count() == 0) {
            log.info("Initializing platform...");

            log.info("Creating platform administrator account...");
            Login admin = accountApplicationService.createUser(new Username("admin"), null, null, "changeit", "平台管理员");

            log.info("Provisioning platform...");
            Tenant tenant = accountApplicationService.provisionTenant(admin, "平台", true);

            accountApplicationService.provisionRoleGroup(tenant.tenantId(), "平台客服",
                    Arrays.asList(Role.PLATFORM_ORDER_READ, Role.PLATFORM_ORDER_WRITE));

            log.info("Platform initialization completed.");
        }
    }

}
