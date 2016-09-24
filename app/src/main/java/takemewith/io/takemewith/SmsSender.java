package takemewith.io.takemewith;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;

import takemewith.io.takemewith.utils.UserPreferences;


public final class SmsSender {

    private static final String TAG = SmsSender.class.getSimpleName();

    private SmsSender() {};

    public static void sendSms(String msg) {
        String number = UserPreferences.get().getEmergencyNumber();
        SmsManager smsManager = SmsManager.getDefault();
        if (TextUtils.isEmpty(number)) {
            Log.e(TAG,".sendSms() - cannot send sms as the number is not configured");
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            Log.e(TAG,".sendSms() - cannot send sms as the msg is empty");
            return;
        }
        smsManager.sendTextMessage(number, null, msg, null, null);
    }
}
