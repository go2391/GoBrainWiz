package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.support.annotation.Nullable;


/**
 */

public class UserUtils {
    public static boolean isUserLogin(Context context) {
        return !SharedPrefUtils.getUserEmail(context).isEmpty();
    }


}
