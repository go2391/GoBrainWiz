package brainwiz.gobrainwiz.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.api.model.BaseModel;

/**
 */
public class DDAlerts {


    public static final int BUTTON_POSITIVE = 0;
    public static final int BUTTON_NEGATIVE = 1;
    private static AlertDialog alertDialog;
    private static Toast toast;


    public static void showAlert(Activity activity, String message, String positiveButton, String negativeButton) {
        showAlert(activity, null, message, positiveButton, negativeButton, null);
    }

    public static void showAlert(Activity activity, String message, String positiveButton, String negativeButton, final AlertListener listener) {
        showAlert(activity, null, message, positiveButton, negativeButton, listener);
    }

    public static void showAlert(Activity activity, String message, String positiveButton, final AlertListener listener) {
        showAlert(activity, null, message, positiveButton, null, listener);
    }

    public static void showAlert(Activity activity, String message, String positiveButton) {
        showAlert(activity, null, message, positiveButton, null, null);
    }

    /**
     * @param activity
     * @param message
     * @param positiveButton -
     * @param negativeButton
     * @param listener
     */
    public static void showAlert(Activity activity, String title, String message, String positiveButton, String negativeButton, final AlertListener listener) {
//        showFlashAlertDialog(activity);

        try {
            if (alertDialog != null)
                alertDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View inflate = activity.getLayoutInflater().inflate(R.layout.inflate_alert, null);
        builder.setView(inflate);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView messageView = (TextView) inflate.findViewById(R.id.alert_message);
        TextView titleView = (TextView) inflate.findViewById(R.id.alert_title);
        if (title != null)
            titleView.setText(title);

        messageView.setText(message);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int whichBtn = -1;
                switch (view.getId()) {
                    case R.id.alert_negative:
                        whichBtn = BUTTON_NEGATIVE;
                        break;
                    case R.id.alert_positive:
                        whichBtn = BUTTON_POSITIVE;
                        break;
                }
                if (listener != null) {
                    listener.onClick(whichBtn);
                }
                alertDialog.dismiss();
            }
        };
        if (positiveButton != null) {
            Button positiveButtonView = (Button) inflate.findViewById(R.id.alert_positive);
            positiveButtonView.setText(positiveButton);
            positiveButtonView.setOnClickListener(clickListener);
            positiveButtonView.setVisibility(View.VISIBLE);
        }

        if (negativeButton != null) {
            Button negativeButtonView = (Button) inflate.findViewById(R.id.alert_negative);
            negativeButtonView.setText(negativeButton);
            negativeButtonView.setOnClickListener(clickListener);
            negativeButtonView.setVisibility(View.VISIBLE);
        }


        alertDialog.show();
    }

    private static void initAlertDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View inflate = activity.getLayoutInflater().inflate(R.layout.inflate_alert, null);
        builder.setView(inflate);
        alertDialog = builder.create();
    }

    public static void showToast(Context context, String message) {
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (!message.isEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        }
    }

    public static void showFieldsAlert(Activity context, String message) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        showAlert(context, message, context.getString(R.string.ok));
    }

    public static void showResponseError(Context context, BaseModel baseModel) {

        String text = baseModel.getMessage();

        Toast.makeText(context, text != null && !text.isEmpty() ? text : "Internal server error.", Toast.LENGTH_SHORT).show();
    }

    public static void showResponseError(Activity activity, String message) {
        if (!message.isEmpty() && activity != null) {
            showAlert(activity, message, activity.getString(R.string.ok));
        }
    }

    /*public static void showResponseError(Activity context, JSONObject jsonObject, boolean alert) {
        if (alert) {

            String message = jsonObject.optString("description");
            showAlert(context, message.isEmpty() ? jsonObject.optString("discription") : message, context.getString(R.string.ok));
        } else {
            showResponseError(context, jsonObject);
        }
    }*/

    public static void showNetworkAlert(Activity context) {
        Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
    }

    public static void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public static void showGPSAlert(final Activity context) {
        showAlert(context, "Please enable location services", "Enable", new AlertListener() {
            @Override
            public void onClick(int buttonType) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);

            }
        });
    }


    public interface AlertListener {
        void onClick(int buttonType);

    }
}
