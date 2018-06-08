package com.feng.accounts.adapter.rpc;

import com.feng.accounts.application.representation.MemberRpcRepresentation;
import com.feng.accounts.application.representation.TenantRpcRepresentation;
import com.feng.accounts.application.service.AccountApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rpc")
@Slf4j
public class RpcController {

    private final AccountApplicationService accountApplicationService;

    @Autowired
    public RpcController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tenants/{tenantId}")
    public TenantRpcRepresentation getTenant(@PathVariable String tenantId) {
        log.info("RPC getting tenant: {}", tenantId);
        return accountApplicationService.getTenant(tenantId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tenants/{tenantId}/members/{memberId}")
    public MemberRpcRepresentation getMember(@PathVariable String tenantId, @PathVariable String memberId) {
        log.info("RPC getting member: tenantId = {}, memberId = {}", tenantId, memberId);
        return accountApplicationService.getMember(tenantId, memberId);
    }

}
