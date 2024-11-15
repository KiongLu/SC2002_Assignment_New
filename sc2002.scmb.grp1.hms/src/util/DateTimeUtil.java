package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    // Method to parse date in DD-MM-YYYY format and check if it's valid
    public static LocalDate parseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            //System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return null;
        }
    }

    // Method to parse time in HH:mm format and check if it's valid
    public static LocalTime parseTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            //System.out.println("Invalid time format. Please use HH:mm.");
            return null;
        }
    }

    // Check if the date is valid (Optional)
    public static boolean isValidDate(String date) {
        return parseDate(date) != null;
    }

    // Check if the time is valid (Optional)
    public static boolean isValidTime(String time) {
        return parseTime(time) != null;
    }
}
