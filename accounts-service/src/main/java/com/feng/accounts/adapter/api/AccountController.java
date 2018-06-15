package com.feng.accounts.adapter.api;

import com.feng.accounts.application.command.UserCreatedCommand;
import com.feng.accounts.application.representation.UsersInfoRepresentation;
import com.feng.accounts.application.service.AccountApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class AccountController {

    private final AccountApplicationService accountApplicationService;

    @Autowired
    public AccountController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @PostMapping("/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreatedCommand command) {
        log.info("create user , userInfo : {}", command);
        accountApplicationService.createUser(command.getUsername(), command.getCellphone(), command.getEmail(),
                command.getPassword(), command.getNickName(), command.getWechatOpenId());
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("#oauth2.hasAnyScope('user', 'platform')")
    public UsersInfoRepresentation userInfo(@AuthenticationPrincipal(expression = "username") String username){
        log.debug("get userInfo by username : {}", username);
        return accountApplicationService.userInfo(username);
    }



//    Example
//    @PreAuthorize("#oauth2.hasAnyScope('tenant', 'platform') and isAuthenticated()")
//    @GetMapping("/info")
//    public Map<String, Object> userInfo(@AuthenticationPrincipal(expression = "tenantId") String tenantId,
//                                        @AuthenticationPrincipal(expression = "tenantType") String tenantType,
//                                        @AuthenticationPrincipal(expression = "tenantStatus") String tenantStatus,
//                                        @AuthenticationPrincipal(expression = "username") String username,
//                                        @AuthenticationPrincipal(expression = "authorities") Collection<? extends GrantedAuthority> authorities) {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("username", username);
//        result.put("tenantId", tenantId);
//        result.put("tenantType", tenantType);
//        result.put("tenantStatus", tenantStatus);
//        result.put("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(toList()));
//        result.put("friendlyName", accountApplicationService.friendlyName(username));
//
//        return result;
//    }

}
