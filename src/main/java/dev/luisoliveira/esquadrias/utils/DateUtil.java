package dev.luisoliveira.esquadrias.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public static boolean isValidBirthDate(String birthDate) {
        String datePattern = "^\\d{2}-\\d{2}-\\d{4}$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(birthDate.trim());
        return matcher.matches();
    }
}
