package brainwiz.gobrainwiz.utils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IBLE 1 on 30-Aug-16.
 */
public class InputValidation {

    static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    static String NAME_PATTERN = "^[a-zA-Z]{2,}(?: [a-zA-Z]+){0,2}$";

    // validating email id
    public static boolean isValidEmail(String email) {


        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating name
    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // validating password with retype password
    public static boolean isValidPassword(String pass) {
        return pass != null && pass.length() >= 8;
    }


    // validating email id
    public static boolean isValidEmailOrMobile(String input) {


        boolean result;
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(input);
        result = matcher.matches();

        if (!result) {
            result = isValidMobileNo(input);
        }

        return result;
    }


    // validating mobile no
    public static boolean isValidMobileNo(String mobileNo) {
        if (mobileNo != null) {
            String str = mobileNo.replaceAll("\\D+", "");
            return str.length() >= 10 && Long.parseLong(str) > 0;
        }
        return false;
    }

    // validating card no
    public static boolean isValidCardNo(String cardNo) {
        return cardNo != null && cardNo.length() > 14 && Long.parseLong(cardNo) > 0;
    }

    // validating card no
    public static boolean isValidCardDate(String date) {
        if (date != null && date.length() > 4) {
            String[] split = date.split("/");
            Calendar instance = Calendar.getInstance();

            int year = Integer.parseInt("20" + split[1]);
            int month = Integer.parseInt(split[0]);

            return !(month > 12 || month == 0) && (year > instance.get(Calendar.YEAR) || (year == instance.get(Calendar.YEAR) && instance.get(Calendar.MONTH) < month));

        }
        return false;
    }

    // validating card no
    public static boolean isValidCardCvv(String cvv) {
        return cvv != null && cvv.length() >= 3 && Integer.parseInt(cvv) > 0 ;
    }

    public static boolean isValidZip(String zip) {
        return zip != null && zip.length() == 5 && Integer.parseInt(zip) > 0 ;
    }
    public static boolean isValidAddress(String zip) {
        return zip != null && zip.length() >= 3;
    }
}
