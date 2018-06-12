package com.feng.accounts.adapter.api;

import com.feng.accounts.application.command.ResetPasswordCommand;
import com.feng.accounts.application.service.AccountApplicationService;
import com.feng.accounts.model.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platform/v1")
public class PlatformAccountController {

    @Autowired
    private AccountApplicationService accountApplicationService;

    @PreAuthorize("#oauth2.hasScope('platform') and hasRole('ROLE_PLATFORM_TENANT_ACCOUNT_MANAGEMENT')")
    @PutMapping("/users/{username}/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable String username, @RequestBody ResetPasswordCommand command) {
        accountApplicationService.updatePassword(new Username(username), command.getPassword());
    }

}
