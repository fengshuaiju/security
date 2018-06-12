package com.feng.accounts.adapter.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by minggaoxi on 22/08/2017.
 */
public interface InputChannels {

    String INPUT_FROM_SELF = "from-self";

    @Input(InputChannels.INPUT_FROM_SELF)
    SubscribableChannel inputFromSelf();

}
