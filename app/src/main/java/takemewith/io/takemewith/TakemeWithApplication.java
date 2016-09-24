package takemewith.io.takemewith;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

import takemewith.io.takemewith.utils.UserPreferences;

public class TakemeWithApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        UserPreferences.init(getApplicationContext());
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect((new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Start monitoring regions
                beaconManager.startMonitoring(BeaconsData.ROOM1_REGION);
                beaconManager.startMonitoring(BeaconsData.ROOM2_REGION);
                beaconManager.startRanging(BeaconsData.TAG_BEACON_REGION);
            }
        }));

        beaconManager.setRangingListener(new SirenPlayingRangeListener(
                new SirenPlayer(getApplicationContext()),
                BeaconsData.TAG_BEACON_REGION.getIdentifier()));

        // SAMPLE LISTENER FOR THE REGIONS
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            //Sample listener
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                if (region.equals(BeaconsData.ROOM1_REGION)) {
                    // Region 1 shows a toast
                    Toast.makeText(getApplicationContext(), "Region 1 entered", Toast.LENGTH_LONG).show();
                } else if (region.equals(BeaconsData.ROOM2_REGION)) {
                    // Region 2 shows a notification
                    showNotification("New Region!", "Region 2 Entered");
                }
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
                Toast.makeText(getApplicationContext(), "Region 1 exited", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(001, builder.build());

    }

}