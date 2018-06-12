package com.feng.accounts.adapter.api;

import com.feng.accounts.application.service.AccountApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final AccountApplicationService accountApplicationService;

    @Autowired
    public MemberController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }


}
