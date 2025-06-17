package adapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Adapter pattern implementation for converting between different date/time formats
 * Used to adapt legacy Date objects to modern LocalDateTime and vice versa
 */
public class DateTimeAdapter {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    /**
     * Adapts Date to LocalDateTime
     */
    public static LocalDateTime adaptToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }
    
    /**
     * Adapts LocalDateTime to Date
     */
    public static Date adaptToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * Adapts String to LocalDateTime
     */
    public static LocalDateTime adaptStringToLocalDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeString, DEFAULT_FORMATTER);
    }
    
    /**
     * Adapts LocalDateTime to String
     */
    public static String adaptLocalDateTimeToString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DEFAULT_FORMATTER);
    }
    
    /**
     * Adapts String to Date
     */
    public static Date adaptStringToDate(String dateTimeString) {
        LocalDateTime localDateTime = adaptStringToLocalDateTime(dateTimeString);
        return adaptToDate(localDateTime);
    }
    
    /**
     * Adapts Date to String
     */
    public static String adaptDateToString(Date date) {
        LocalDateTime localDateTime = adaptToLocalDateTime(date);
        return adaptLocalDateTimeToString(localDateTime);
    }
}