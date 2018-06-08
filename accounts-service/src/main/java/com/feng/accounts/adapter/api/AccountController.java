package com.feng.accounts.adapter.api;

import com.feng.accounts.application.command.BindWechatCommand;
import com.feng.accounts.application.service.AccountApplicationService;
import com.feng.accounts.model.TenantId;
import com.feng.accounts.model.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/v1/users")
public class AccountController {

    private final AccountApplicationService accountApplicationService;

    @Autowired
    public AccountController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @PreAuthorize("#oauth2.hasAnyScope('tenant', 'platform') and isAuthenticated()")
    @GetMapping("/info")
    public Map<String, Object> userInfo(@AuthenticationPrincipal(expression = "tenantId") String tenantId,
                                        @AuthenticationPrincipal(expression = "tenantType") String tenantType,
                                        @AuthenticationPrincipal(expression = "tenantStatus") String tenantStatus,
                                        @AuthenticationPrincipal(expression = "username") String username,
                                        @AuthenticationPrincipal(expression = "authorities") Collection<? extends GrantedAuthority> authorities) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("tenantId", tenantId);
        result.put("tenantType", tenantType);
        result.put("tenantStatus", tenantStatus);
        result.put("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(toList()));
        result.put("friendlyName", accountApplicationService.friendlyName(username));

        return result;
    }

    @PreAuthorize("#oauth2.hasAnyScope('frontend', 'tenant', 'platform')")
    @PostMapping("/{username}/captcha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendCaptcha(@PathVariable String username) {
        accountApplicationService.sendCaptcha(username);
    }

    @PreAuthorize("#oauth2.hasScope('frontend')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> registerUser(@RequestBody Registration registration) {
        TenantId tenantId = accountApplicationService.createTenant(
                registration.getUsername(),
                registration.getPassword(),
                registration.getTenantName(),
                registration.getCaptcha());
//        return ImmutableMap.of("tenantId", tenantId.toString());
        return null;
    }

    @PreAuthorize("#oauth2.hasAnyScope('tenant', 'platform')")
    @PostMapping("/bind-wechat")
    @ResponseStatus(HttpStatus.CREATED)
    public void bindWechat(@RequestBody BindWechatCommand bindWechatCommand,
                           @AuthenticationPrincipal(expression = "username") String username) {
        accountApplicationService.bindWechat(bindWechatCommand, new Username(username));
    }

}
