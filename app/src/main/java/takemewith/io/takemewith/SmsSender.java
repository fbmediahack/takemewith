package takemewith.io.takemewith;

import android.telephony.SmsManager;

import takemewith.io.takemewith.utils.UserPreferences;


public final class SmsSender {

    private SmsSender() {};

    public static void sendSms() {
        String number = UserPreferences.get().getEmergencyNumber();
        String name = UserPreferences.get().getName();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, String.format("%s has left the building!!", name),
                null,
                null);
    }
}
