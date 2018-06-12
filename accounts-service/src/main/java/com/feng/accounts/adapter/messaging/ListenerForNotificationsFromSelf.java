package com.feng.accounts.adapter.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Created by fengshuaiju on 17/8/23.
 */
@Slf4j
@EnableBinding(InputChannels.class)
public class ListenerForNotificationsFromSelf {

    @StreamListener(InputChannels.INPUT_FROM_SELF)
    public void handleNotificationsFromSelf(Notification notification) throws ClassNotFoundException {

        log.error("Notification from self received: {}", notification.toString());

        try {
            EventReader eventReader = new EventReader(notification.getContent());

            switch (notification.getTypeName()) {
                case "com.feng.accounts.model.TenantApproved": {
                    String chineseName = eventReader.stringValue("chineseName").get();
                    log.info("get content info > chineseName :{}", chineseName);
                    break;
                }

                default:
                    log.debug("Unhandled notification: {}", notification.getTypeName());
                    break;
            }

        } catch (Exception e) {
            log.error("Caught exception: ", e);
        }
    }
}
