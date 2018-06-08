package com.feng.accounts.support.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class DomainEventListener {

    @Autowired
    private ObjectMapper jsonSerdes;
    

    @EventListener
    @Transactional
    public void saveEventToPublish(DomainEvent event) {
//        String eventJson = Try.of(() -> jsonSerdes.writeValueAsString(event)).get();
//        jooq.insertInto(EVENT_TO_PUBLISH)
//                .set(EVENT_TO_PUBLISH.NAME, event.getClass().getSimpleName())
//                .set(EVENT_TO_PUBLISH.CONTENT, eventJson)
//                .execute();
    }

}
