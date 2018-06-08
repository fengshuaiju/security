package com.feng.accounts.support.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;

@Converter(autoApply = true)
public class InstantTimestampConverter implements AttributeConverter<Instant, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(Instant attribute) {
        return attribute == null ? null : Timestamp.from(attribute);
    }

    @Override
    public Instant convertToEntityAttribute(Timestamp dbData) {
        return dbData == null ? null : dbData.toInstant();
    }
}
