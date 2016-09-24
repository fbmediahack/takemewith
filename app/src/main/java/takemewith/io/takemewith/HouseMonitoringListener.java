package takemewith.io.takemewith;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

public class HouseMonitoringListener implements BeaconManager.MonitoringListener {

    private final Context mContext;

    public HouseMonitoringListener(Context context) {
        mContext = context;
    }

    @Override
    public void onEnteredRegion(Region region, List<Beacon> list) {
        if (region.equals(BeaconsData.ROOM_REGION)) {
            Toast.makeText(mContext, "House entered", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onExitedRegion(Region region) {
        if (region.equals(BeaconsData.ROOM_REGION)) {
            //Toast.makeText(getApplicationContext(), "House exited", Toast.LENGTH_LONG).show();
            showNotification("house exited", "You left the house!");
            //SmsSender.sendSms();
        }
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(mContext, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(mContext, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(001, builder.build());

    }
}
