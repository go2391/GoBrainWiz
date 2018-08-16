package brainwiz.gobrainwiz.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class StringUtills {
public static String getUrlString(String url, String[] values)
    {
        String urlResult = url;
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if(value!=null){
                urlResult = urlResult.replaceFirst("~", value);
            }
            Log.i("string", url.indexOf("~") + "" + urlResult);
        }
        return urlResult;
    }

    public static String formatDecimal(float f) {
        return String.format("%.2f",f);
    }
    public static String formatDecimal(double f) {
        return String.format("%.2f",f);
    }

    @NonNull
    public static String formatMobileNo(String s) {
        String str = s.replaceAll("\\D+", "");

        if (str.length() > 3) {
            s = "(" + str.substring(0, 3) + ") " + str.substring(3, str.length());
        }
        if (str.length() > 6) {
            s = "(" + str.substring(0, 3) + ") " + str.substring(3, 6) + "-" + str.substring(6, str.length());
        }
        return s;
    }

    @NotNull
    public static String addSlashForCardExpiry(@NotNull String s) {
        String str = s.replaceAll("\\D+", "");
        if (str.length() > 2) {
            s = str.substring(0, 2) + "/" + str.substring(2, str.length());
        }
        return s;
    }
}
