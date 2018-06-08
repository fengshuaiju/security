package com.feng.accounts.adapter.messaging;

import com.feng.accounts.support.domain.MessageSender;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(Source.class)
public class SpringCloudStreamMessageSender implements MessageSender {

    private final Source source;

    @Autowired
    public SpringCloudStreamMessageSender(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Source source) {
        this.source = source;
    }

    @Override
    public void send(String name, String event) {
        Message<String> message = MessageBuilder
                .withPayload(event)
                .setHeader(MessageHeaders.CONTENT_TYPE, "application/json")
                .setHeader("eventName", name)
                .build();
        Boolean succeeded = Try.of(() -> source.output().send(message, 500L)).get();
        if (!succeeded) {
            throw new RuntimeException("Spring Cloud Stream sending message failed");
        }
    }
}
