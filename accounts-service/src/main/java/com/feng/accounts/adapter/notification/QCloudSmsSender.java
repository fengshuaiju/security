package com.feng.accounts.adapter.notification;

import com.feng.accounts.application.service.SmsSender;
import com.feng.accounts.model.Cellphone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QCloudSmsSender implements SmsSender {
    @Override
    public void send(Cellphone cellphone, int templateId, String... args) {

    }

//    private SmsSingleSender sender;
//    private String sign;
//
//    public QCloudSmsSender(@Value("${qcloud.sms.appId}") Integer appId,
//                           @Value("${qcloud.sms.appKey}") String appKey,
//                           @Value("${qcloud.sms.sign}") String sign) {
//        sender = new SmsSingleSender(appId, appKey);
//        this.sign = sign;
//    }
//
//    @Override
//    @Async
//    public void send(Cellphone cellphone, int templateId, String... args) {
//        try {
//            SmsSingleSenderResult result = sender.sendWithParam("86", cellphone.cellphone(), templateId, args, sign, "", "");
//            if (result.result != 0) {
//                log.error("Send message (templateId = {}) to {} failed: {}", templateId, cellphone.cellphone(), result.errMsg);
//            }
//        } catch (Exception e) {
//            log.error("Send message (templateId = {}) to {} threw exception.", templateId, cellphone.cellphone());
//            log.error("Exception:", e);
//        }
//    }



}
