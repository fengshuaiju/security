package com.feng.accounts.adapter.api;

import com.feng.accounts.model.Tenant;
import com.feng.accounts.model.TenantApproved;
import com.feng.accounts.support.domain.DomainEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fengshuaiju on 2018-06-09.
 */
@RestController
@RequestMapping("/v1/open")
public class OpenController {

    @GetMapping("/message")
    public void sendMessage(){
        DomainEventPublisher.publish(
                TenantApproved.builder()
                .chineseName("冯帅炬")
                .code("CODE")
                .id("ID")
                .type(Tenant.Type.PREMIER_CUSTOMER)
                .build()
        );
    }


}
