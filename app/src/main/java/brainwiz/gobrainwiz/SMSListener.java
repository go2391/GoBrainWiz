package brainwiz.gobrainwiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSListener extends BroadcastReceiver {

    private static OtpPasswordFragment.Common.OTPListener mListener; // this listener will do the magic of throwing the extracted OTP to all the bound views.

    @Override
    public void onReceive(Context context, Intent intent) {

        // this function is trigged when each time a new SMS is received on device.

        Bundle data = intent.getExtras();

        Object[] pdus = new Object[0];
        if (data != null) {
            pdus = (Object[]) data.get("pdus"); // the pdus key will contain the newly received SMS
        }

        if (pdus != null) {
            for (Object pdu : pdus) { // loop through and pick up the SMS of interest
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);


                // your custom logic to filter and extract the OTP from relevant SMS - with regex or any other way.

                if (mListener != null)
                    mListener.onOTPReceived(smsMessage.getDisplayMessageBody().substring(0, 4));
                break;
            }
        }
    }

    public static void bindListener(OtpPasswordFragment.Common.OTPListener listener) {
        mListener = listener;
    }

    public static void unbindListener() {
        mListener = null;
    }
}
