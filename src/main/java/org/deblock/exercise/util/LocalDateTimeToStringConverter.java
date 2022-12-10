package org.deblock.exercise.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class LocalDateTimeToStringConverter extends StdConverter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime value) {
        return ZonedDateTime.ofLocal(value, TimeZone.getDefault().toZoneId(), ZoneOffset.UTC).toString();
    }
}