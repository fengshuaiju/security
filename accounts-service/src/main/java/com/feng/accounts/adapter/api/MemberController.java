package com.feng.accounts.adapter.api;

import com.feng.accounts.application.command.AddMemberCommand;
import com.feng.accounts.application.representation.MemberRepresentation;
import com.feng.accounts.application.service.AccountApplicationService;
import com.feng.accounts.model.Cellphone;
import com.feng.accounts.model.EmailAddress;
import com.feng.accounts.model.TenantId;
import com.feng.accounts.model.Username;
import com.feng.accounts.support.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final AccountApplicationService accountApplicationService;

    @Autowired
    public MemberController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @PreAuthorize("#oauth2.hasScope('tenant') and hasRole('ROLE_TENANT_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addMember(@AuthenticationPrincipal(expression = "tenantId") String tenantId,
                                         @RequestBody AddMemberCommand command) {
        Username memberId = accountApplicationService.addMember(new TenantId(tenantId),
                command.getName(),
                command.getCellphone() == null ? null : new Cellphone(command.getCellphone()),
                new EmailAddress(Validate.notBlank(command.getEmail(), "error.member.email.empty")),
                Validate.notBlank(command.getPassword(), "error.password.empty"),
                Validate.notEmpty(command.getRoleGroups(), "error.roleGroups.empty"));
//        return ImmutableMap.of("memberId", memberId.toString());
        return null;
    }

    @PreAuthorize("#oauth2.hasScope('tenant') and hasRole('ROLE_TENANT_ADMIN')")
    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@AuthenticationPrincipal(expression = "tenantId") String tenantId,
                             @PathVariable String memberId) {
        accountApplicationService.deleteMember(new TenantId(tenantId), memberId);
    }

    @PreAuthorize("#oauth2.hasScope('tenant') and hasRole('ROLE_TENANT_ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<MemberRepresentation> getMembers(@AuthenticationPrincipal(expression = "tenantId") String tenantId,
                                                 Pageable pageable) {
        return accountApplicationService.getMembers(new TenantId(tenantId), pageable);
    }

}
