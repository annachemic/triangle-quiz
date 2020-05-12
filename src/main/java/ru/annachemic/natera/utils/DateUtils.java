package ru.annachemic.natera.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@UtilityClass
public class DateUtils {
    public Instant getInstantDateTime(Long timestamp) {
        Long epoch = timestamp / 1000;
        return Instant.ofEpochSecond(epoch);
    }

    public String formatDate(Instant instant) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(Locale.UK)
                        .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public String formatDate(Long ts) {
        return formatDate(getInstantDateTime(ts));
    }
}
