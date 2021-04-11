package by.bivis.kbp.parser.utils;

import java.time.LocalDate;

public class DateUtils {
    private DateUtils() {
    }

    /**
     * Returns day number. Monday is 1, Sunday is 7
     *
     * @return the day number
     */
    public static int getDayNumber(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    public static long getCurrentUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public static int getKbpScheduleDayNumber(LocalDate localDate) {
        int dayNumber =  getDayNumber(localDate);
        if (dayNumber == 7) {
            return 1;
        } else {
            return dayNumber;
        }
    }
}
