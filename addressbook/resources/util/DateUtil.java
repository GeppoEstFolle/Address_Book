package addressbook.resources.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Helper functions for handling dates;
 *
 * @author Giuseppe Serra
 */
public class DateUtil {
    
    /**
     * The date pattern that is used for covertion.
     * Change as you wish.
     */
    private final static String DATE_PATTERN = "dd-MM-yyyy";

    /**
     * The date formatter.
     */
    private static final  DateTimeFormatter DATE_FORMATTER = 
                       DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ITALY);

    /**
     * Returns the given date as a well formatted String.
     * @param date of person;
     * @return the date to be returned as a string;
     */
    public static String  format(LocalDate date){
        if(date==null){
            return null;
        }
        return DATE_FORMATTER.format(date);
    }  
    
    /**
     * Converts a String in the format of the defined 
     * in LOcalDate object.
     * @param dateString string of data;
     * @return the date object or null if it could not be converted;
     */
    public static LocalDate parse(String dateString){
        try{
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        }catch(DateTimeParseException e){
            return null;
        }
    }
    
    /**
     * Checks the String whether it is a valid date.
     * @param dateString.
     * @return true if the String is a valid date.
     */
    public static boolean validDate(String dateString){
       return DateUtil.parse(dateString)!= null;
    }
}