package com.feng.accounts.adapter.api;

import com.feng.accounts.application.command.UserRegisterCommand;
import com.feng.accounts.application.service.AccountApplicationService;
import com.feng.accounts.model.Login;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/open")
public class OpenController {

    @Value("${wechat.appId}")
    public String appId;

    @Value("${wechat.appSecret}")
    public String appSecret;

    private final AccountApplicationService accountApplicationService;

    @Autowired
    public OpenController(AccountApplicationService accountApplicationService){
        this.accountApplicationService = accountApplicationService;
    }

    @PostMapping("/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createUser(@RequestBody UserRegisterCommand command) {
        log.info("register user , userInfo : {}", command);
        Login login = accountApplicationService.registerUser(command.getUsername(), command.getPassword(), command.getNickName());
        log.debug("register user success: {}", login.username());

        return ImmutableMap.of(
                "code", 0,
                "msg","ok",
                "data", login.username().username()
        );
    }

    @GetMapping("/info")
    public Object info(@RequestParam String code){
        return ImmutableMap.of("info","ok");
    }

}
