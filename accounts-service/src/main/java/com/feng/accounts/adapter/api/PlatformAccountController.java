package com.feng.accounts.adapter.api;

import com.feng.accounts.application.command.ApproveTenantCommand;
import com.feng.accounts.application.command.CreateTenantCommand;
import com.feng.accounts.application.command.ResetPasswordCommand;
import com.feng.accounts.application.representation.TenantRepresentation;
import com.feng.accounts.application.service.AccountApplicationService;
import com.feng.accounts.model.Tenant;
import com.feng.accounts.model.TenantId;
import com.feng.accounts.model.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/platform/v1")
public class PlatformAccountController {

    @Autowired
    private AccountApplicationService accountApplicationService;

    @PreAuthorize("#oauth2.hasScope('platform') and hasRole('ROLE_PLATFORM_TENANT_ACCOUNT_MANAGEMENT')")
    @PostMapping("/tenants")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTenant(@AuthenticationPrincipal(expression = "username") String username,
                             @RequestBody CreateTenantCommand command) {
        Username supporter = new Username(username);
        accountApplicationService.createAndApproveTenant(command.getAdminUsername(), command.getPassword(),
                command.getAdminUsername(), command.getTenantChineseName(), command.getRemarks(), supporter);
    }

    @PreAuthorize("#oauth2.hasScope('platform') and hasRole('ROLE_PLATFORM_TENANT_ACCOUNT_MANAGEMENT')")
    @GetMapping("/tenants")
    @ResponseStatus(HttpStatus.OK)
    public Page<TenantRepresentation> findByTenantName(@AuthenticationPrincipal(expression = "username") String username,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "status", required = false) String status,
                                                       Pageable pageable) {
        return accountApplicationService.searchTenants(new Username(username), name, status == null ? null : Tenant.Status.valueOf(status), pageable);
    }

    @PreAuthorize("#oauth2.hasScope('platform') and hasRole('ROLE_PLATFORM_TENANT_ACCOUNT_MANAGEMENT')")
    @PutMapping("/tenants/{tenantId}/approval")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveTenant(@AuthenticationPrincipal(expression = "username") String username,
                              @PathVariable String tenantId,
                              @RequestBody ApproveTenantCommand command) {
        accountApplicationService.approveTenant(new Username(username), new TenantId(UUID.fromString(tenantId)),
                command.isApproved(), Tenant.Type.PREMIER_CUSTOMER, command.getCode(), command.getRemarks());
    }

    @PreAuthorize("#oauth2.hasScope('platform') and hasRole('ROLE_PLATFORM_TENANT_ACCOUNT_MANAGEMENT')")
    @PatchMapping("/tenants/{tenantId}")
    @ResponseStatus(HttpStatus.OK)
    public void disableTenant(@PathVariable String tenantId, @RequestParam boolean enabled) {
        accountApplicationService.enableTenant(tenantId, enabled);
    }

    @PreAuthorize("#oauth2.hasScope('platform') and hasRole('ROLE_PLATFORM_TENANT_ACCOUNT_MANAGEMENT')")
    @PutMapping("/users/{username}/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable String username, @RequestBody ResetPasswordCommand command) {
        accountApplicationService.updatePassword(new Username(username), command.getPassword());
    }

}
