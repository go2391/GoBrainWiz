package brainwiz.gobrainwiz.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Purushotam on 2/13/2017.
 */

public class DateUtils {

    public static String getFormattedDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd yyyy", Locale.US);
        return dateFormat.format(c.getTime());
    }
}
