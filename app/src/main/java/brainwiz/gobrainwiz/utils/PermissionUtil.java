package brainwiz.gobrainwiz.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

/**
 */

public class PermissionUtil {

    public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/brainwiz/";

    // Storage Permissions
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // Location Permissions
    public static final int REQUEST_LOCATION = 2;
    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyLocationPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LOCATION,
                    REQUEST_LOCATION
            );
        }
    }

    /**
     * Checks whether runtime permissions must be handled or not.
     *
     * @param context Application context.
     * @return Handle runtime permissions or not.
     */
    public static boolean shouldCheckRuntimePermissions(Context context) {
        return
                isApplicationWithMarshmallowTargetSdkVersion(context) &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Checks whether the app is compiled with targetSdkVersion Marshmallow or above.
     *
     * @param context Application context.
     * @return Application targetSdkVersion above 23 or not.
     */
    public static boolean isApplicationWithMarshmallowTargetSdkVersion(Context context) {
        return
                context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M;
    }

}
