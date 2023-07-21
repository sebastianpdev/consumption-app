package com.jspapps.consumptionapp.domain.usecase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KindPeriodHandle {

    interface PeriodFormatter {
        List<String> buildPeriodFormat(List<LocalDate> dates);
    }

    /**
     * Clase para aplicar a las fechas el formato mensual
     */
    public static class PeriodMonthlyFormat implements PeriodFormatter {

        private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM yyyy");

        @Override
        public List<String> buildPeriodFormat(List<LocalDate> dates) {
            List<String> datesFormatted = new ArrayList<>();
            for (LocalDate date: dates) {
                datesFormatted.add(date.format(dateFormat).toUpperCase());
            }

            datesFormatted = datesFormatted.stream().distinct().collect(Collectors.toList());
            return datesFormatted;
        }
    }

    /**
     * Clase para aplicar a las fechas el formato semanal
     */
    public static class PeriodWeeklyFormat implements PeriodFormatter {

        private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d");

        @Override
        public List<String> buildPeriodFormat(List<LocalDate> dates) {
            List<String> datesFormatted = new ArrayList<>();

            List<String> tempDateFormatted = new ArrayList<>();
            for (LocalDate date: dates) {
                tempDateFormatted.add(date.format(dateFormat).toUpperCase());
            }

            for (int i = 0; i < tempDateFormatted.size(); i+=7) {
                String date = "";
                date = date.concat(String.valueOf(tempDateFormatted.get(i)));

                if (i + 6 < tempDateFormatted.size()) {
                    date = date.concat(" - " + tempDateFormatted.get(i + 6));
                }
                datesFormatted.add(date);
            }

            return datesFormatted;
        }
    }

    /**
     * Clase para aplicar a las fechas el formato diario
     */
    public static class PeriodDailyFormat implements PeriodFormatter {

        private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d");

        @Override
        public List<String> buildPeriodFormat(List<LocalDate> dates) {
            List<String> datesFormatted = new ArrayList<>();
            for (LocalDate date: dates) {
                datesFormatted.add(date.format(dateFormat).toUpperCase());
            }

            datesFormatted = datesFormatted.stream().distinct().collect(Collectors.toList());
            return datesFormatted;
        }
    }

    /**
     * Clase encargada de controlar el formato segun el periodo solicitdao, usando el patron strategy
     */
    public static class KindPeriodContext {
        private PeriodFormatter periodFormatter;
        private List<LocalDate> dates;

        public void setPeriodFormatter(PeriodFormatter periodFormatter) {
            this.periodFormatter = periodFormatter;
        }

        public void prepareDates(List<Instant> dates) {
            var mDates = dates.stream().map(d -> d.atZone(ZoneId.systemDefault()).toLocalDate()).collect(Collectors.toList());
            mDates = mDates.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            mDates = mDates.stream().distinct().collect(Collectors.toList());
            this.dates = mDates;
        }

        public List<String> buildPeriodSection() {
            return periodFormatter.buildPeriodFormat(dates);
        }
    }
}
