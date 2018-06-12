package com.feng.authserver.support.persistence;

import org.jooq.Converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class JooqOffsetDateTimeToInstantConverter implements Converter<OffsetDateTime, Instant> {

    @Override
    public Instant from(OffsetDateTime databaseObject) {
        return databaseObject == null ? null : databaseObject.toInstant();
    }

    @Override
    public OffsetDateTime to(Instant userObject) {
        return userObject == null ? null : OffsetDateTime.ofInstant(userObject, ZoneId.of("Asia/Shanghai"));
    }

    @Override
    public Class<OffsetDateTime> fromType() {
        return OffsetDateTime.class;
    }

    @Override
    public Class<Instant> toType() {
        return Instant.class;
    }

}
