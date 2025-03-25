package platform.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static String generateDateInString(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public static LocalDateTime parseStringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
