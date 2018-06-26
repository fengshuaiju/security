package com.feng.accounts.adapter.messaging;

import com.feng.accounts.support.domain.MessageSender;
import com.google.common.collect.ImmutableMap;
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

    public static final ImmutableMap<String, Object> HEADERS = ImmutableMap.of(MessageHeaders.CONTENT_TYPE, "application/json");

    private final Source source;

    @Autowired
    private JsonSerializer jsonSerializer;

    @Autowired
    public SpringCloudStreamMessageSender(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Source source) {
        this.source = source;
    }

    @Override
    public void send(String name, String event) {

        Notification notification = Notification.builder().typeName(name).content(event).build();

        Message<String> message = MessageBuilder.createMessage(jsonSerializer.serialize(notification), new MessageHeaders(HEADERS));

        Boolean succeeded = Try.of(() -> source.output().send(message, 100L)).get();
        if (!succeeded) {
            throw new RuntimeException("Spring Cloud Stream sending message failed");
        }
    }
}
