package com.feng.accounts.adapter.api;

import com.alibaba.fastjson.JSON;
import com.feng.accounts.application.command.UserRegisterCommand;
import com.feng.accounts.application.representation.Code2OpenId;
import com.feng.accounts.application.service.AccountApplicationService;
import com.feng.accounts.model.Login;
import com.feng.accounts.model.event.UserCreated;
import com.feng.accounts.support.domain.DomainEventPublisher;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private static final String GETOPENOD = "https://api.weixin.qq.com/sns/jscode2session";
    //?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code

    private final AccountApplicationService accountApplicationService;

    @Autowired
    public OpenController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    //使用code请求微信换取用户的openId,并检测该openId的用户是否存在
    @GetMapping("/users/code2openId")
    public Map<String, Object> getOpenIdByCode(@RequestParam String code) {

        ImmutableMap<String, String> data = ImmutableMap.of("appid", appId, "secret",
                appSecret, "js_code", code, "grant_type", "authorization_code");

        String response = HttpRequest.post(GETOPENOD)
                .form(data)
                .body();

        Code2OpenId code2OpenId = JSON.parseObject(response, Code2OpenId.class);

        if (StringUtils.isNotBlank(code2OpenId.getOpenid())) {
            //check user is existence?
            boolean isExistence = accountApplicationService.friendlyName(code2OpenId.getOpenid()) != null;
            return ImmutableMap.of(
                    "code", 0,
                    "existence", isExistence,
                    "openId", code2OpenId.getOpenid()
            );
        } else {
            return ImmutableMap.of(
                    "code", 1,
                    "openId", ""
            );
        }

    }

    //注册用户
    @PostMapping("/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createUser(@RequestBody UserRegisterCommand command) {
        log.info("register user , userInfo : {}", command);
        Login login = accountApplicationService.registerUser(command.getUserName(), command.getPassword(), command.getNickName());
        log.debug("register user success: {}", login.username());
        DomainEventPublisher.publish(
                UserCreated.builder()
                        .userName(command.getUserName())
                        .nickName(command.getNickName())
                        .build()
        );
        return ImmutableMap.of(
                "code", 0,
                "msg", "ok",
                "data", login.username().username()
        );
    }


    @GetMapping("/info")
    public Object info() {
        //DomainEventPublisher.publish(TenantApproved.builder().code("CODE").chineseName("CHINESENAME").build());
        return ImmutableMap.of("code", 0, "msg", "ok");
    }

    @PostMapping("/info")
    public Object post(@RequestBody Map<String, String> mapData) {
        log.info(JSON.toJSONString(mapData));
        return ImmutableMap.of("code", 0, "msg", "ok");
    }

}
