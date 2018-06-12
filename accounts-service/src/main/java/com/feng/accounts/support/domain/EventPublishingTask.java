package com.feng.accounts.support.domain;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import sprout.jooq.generate.tables.records.EventPublishingTrackerRecord;

import static sprout.jooq.generate.Tables.EVENT_PUBLISHING_TRACKER;
import static sprout.jooq.generate.tables.EventToPublish.EVENT_TO_PUBLISH;


@Configuration
@EnableScheduling
@Slf4j
public class EventPublishingTask {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private DSLContext jooq;

    @Scheduled(fixedDelay = 1000L)
    @Transactional
    public void publishing() {
        if (messageSender == null) {
            return;
        }

        EventPublishingTrackerRecord record = jooq.selectFrom(EVENT_PUBLISHING_TRACKER).fetchOne();
        long mostRecentPublishedEventId = record == null ? 0L : record.getMostRecentPublishedEventId();

        // Get and publish all unpublished events since from last publishing time
        final long[] lastPublishedEventId = {-1};
        jooq.selectFrom(EVENT_TO_PUBLISH)
                .where(EVENT_TO_PUBLISH.ID.greaterThan(mostRecentPublishedEventId))
                .orderBy(EVENT_TO_PUBLISH.ID.asc())
                .forEach(event -> {
                    try {
                        messageSender.send(event.getName(), event.getContent());
                        lastPublishedEventId[0] = event.getId();
                    } catch (Exception ex) {
                        log.error("Sending message failed: name = {}, content = {}", event.getName(), event.getContent());
                        log.error("Due to: ", ex);
                    }
                });

        if (lastPublishedEventId[0] > 0) {
            if (record == null) {
                jooq.insertInto(EVENT_PUBLISHING_TRACKER)
                        .set(EVENT_PUBLISHING_TRACKER.MOST_RECENT_PUBLISHED_EVENT_ID, lastPublishedEventId[0])
                        .execute();
            } else {
                record.setMostRecentPublishedEventId(lastPublishedEventId[0]);
                jooq.executeUpdate(record);
            }
        }
    }

}
