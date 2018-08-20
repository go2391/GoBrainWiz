package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import brainwiz.gobrainwiz.api.ApiStringConstants;


public class SharedPrefUtils {
    public static String PREF_NAME = "GoBrainWizPref";

    public static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void putData(Context context, String key, String value) {
        getSharedPref(context).edit().putString(key, value).apply();
    }


    public static String getUserEmail(Context context) {
        return getString(context, ApiStringConstants.EMAIL, "");
    }


    public static String getUserPhone(Context context) {
        return getString(context, ApiStringConstants.PHONE, "");
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


//    public static Set<String> getStringSet(Context context, String key, Set<String> defaultValues) {
//        return getSharedPref(context).getStringSet(key, defaultValues);
//    }

}
