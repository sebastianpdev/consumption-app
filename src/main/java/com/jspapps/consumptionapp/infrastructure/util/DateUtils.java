package com.jspapps.consumptionapp.infrastructure.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class DateUtils {

    public static Instant convertStringToInstant(String localDate) {
        LocalDate mLocalDate = LocalDate.parse(localDate);

        return mLocalDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
