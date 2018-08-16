package brainwiz.gobrainwiz.utils;

import android.util.Log;


/**
 */
public class LogUtils {

    //make this boolean false when u r uploading to playStore
    private static boolean LOG_ENABLE = true;
    private static String TAG = "apshutters";

    public static void e(String message) {
        if (LOG_ENABLE && message != null)
            Log.e(TAG, message);
    }

    public static void d(String message) {
        if (LOG_ENABLE && message != null)
            Log.d(TAG, message);
    }

    public static void i(String message) {
        if (LOG_ENABLE && message != null)
            Log.i(TAG, message);
    }

    public static void v(String message) {
        if (LOG_ENABLE && message != null)
            Log.v(TAG, message);
    }

    public static void w(String message) {
        if (LOG_ENABLE && message != null)
            Log.w(TAG, message);
    }


}
