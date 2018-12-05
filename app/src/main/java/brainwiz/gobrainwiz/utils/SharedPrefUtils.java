package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import brainwiz.gobrainwiz.MainActivity;
import brainwiz.gobrainwiz.api.ApiStringConstants;


public class SharedPrefUtils {
    public static String PREF_NAME = "GoBrainWizPref";
    public static final String IS_FIRST_LAUNCH = "IsFirstLaunch";
    public static final String IS_LOGIN = "IsLogin";
    public static final String USER_ID = "UserID";
    public static final String USER_MOBILE = "UserMObile";
    public static final String USER_NAME = "UserName";
    public static final String USER_TOKEN = "UserToken";
    public static final String USER_COLLEGE = "UserCollege";
    public static final String USER_EMAIL = "UserEmail";
    public static final String PROFILE_IMAGE = "ProfileImage";
    public static final String FIREBASE_TOKEN = "FirebaseToken";
    public static final String NOTIFICATION_COUNT = "NotificationCount";


    public static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void putData(Context context, String key, String value) {
        getSharedPref(context).edit().putString(key, value).apply();
    }


    public static boolean getIsFirstLaunch(Context context) {
        return getBoolean(context, IS_FIRST_LAUNCH, true);
    }

    public static boolean getIsLogin(Context context) {
        return getBoolean(context, IS_LOGIN, false);
    }

    public static String getUserName(Context context) {
        return getString(context, USER_NAME, "");
    }

    public static String getUserEmail(Context context) {
        return getString(context, USER_EMAIL, "");
    }


    public static String getUserPhone(Context context) {
        return getString(context, USER_MOBILE, "");
    }

    public static String getUserCollege(Context context) {
        return getString(context, USER_COLLEGE, "");
    }
//    public static void putData(Context context, String key, Set<String> value) {
//        getSharedPref(context).edit().putStringSet(key, value).commit();
//    }

    public static void putData(Context context, String key, int value) {
        getSharedPref(context).edit().putInt(key, value).commit();
    }

    public static void putData(Context context, String key, boolean value) {
        getSharedPref(context).edit().putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getSharedPref(context).getString(key, defaultValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getSharedPref(context).getBoolean(key, defaultValue);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPref(context).getInt(key, defaultValue);
    }

    public static void clear(Context context) {
        getSharedPref(context).edit().clear().commit();
    }

    public static String getToken(Context context) {

        return getString(context, USER_TOKEN, "");
    }

    public static String getStudentID(Context context) {
        return getString(context, USER_ID, "");
    }

    public static String getNotificationCount(Context context) {
        return getSharedPref(context).getString(NOTIFICATION_COUNT, "0");
    }


//    public static Set<String> getStringSet(Context context, String key, Set<String> defaultValues) {
//        return getSharedPref(context).getStringSet(key, defaultValues);
//    }

}
