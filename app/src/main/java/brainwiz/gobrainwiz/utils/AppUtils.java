package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.provider.Settings;

public class AppUtils {

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
